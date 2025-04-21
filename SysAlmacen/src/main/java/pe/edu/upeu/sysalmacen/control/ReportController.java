package pe.edu.upeu.sysalmacen.control;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.cloudinary.json.JSONObject;
import org.hibernate.query.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pe.edu.upeu.sysalmacen.dtos.ProductoDTO;
import pe.edu.upeu.sysalmacen.dtos.report.ProdMasVendidosDTO;
import pe.edu.upeu.sysalmacen.modelo.MediaFile;
import pe.edu.upeu.sysalmacen.servicio.IMediaFileService;
import pe.edu.upeu.sysalmacen.servicio.IProductoService;
import org.slf4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/reporte")
public class ReportController {
    private final IProductoService productoService;
    private final IMediaFileService mfService;
    private final Cloudinary cloudinary;
    @GetMapping("/pmvendidos")
    public List<ProdMasVendidosDTO> getProductosMasVendidos() {
        return productoService.obtenerProductosMasVendidos();
    }

    
    //APPLICATION_PDF_VALUE APPLICATION_OCTET_STREAM_VALUE
    @GetMapping(value = "/generateReport", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> generateReport() throws ReportGenerationException {
        try {
            byte[] report = productoService.generateReport();
            return ResponseEntity.ok(report);
        } catch (IOException e) {
            throw new ReportGenerationException("Error generating report", e);
        }
    }


    @GetMapping(value = "/readFile/{idFile}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> readFile(@PathVariable("idFile") Long idFile) throws Exception {
        byte[] data = mfService.findById(idFile).getContent();
        return ResponseEntity.ok(data);
    }
    @PostMapping(value = "/saveFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> saveFile(@RequestParam("file") MultipartFile multipartFile) throws Exception {
        //DB
        MediaFile mf = new MediaFile();
        mf.setContent(multipartFile.getBytes());
        mf.setFileName(multipartFile.getOriginalFilename());
        mf.setFileType(multipartFile.getContentType());
        mfService.save(mf);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/saveFileCloud", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> saveFileCloud(@RequestParam("file") MultipartFile multipartFile) throws Exception {
    //Repo Externo / Cloudinary
        File f = this.convertToFile(multipartFile);
        Map<String, Object> response = cloudinary.uploader().upload(f, ObjectUtils.asMap("resource_type", "auto"));
        JSONObject json = new JSONObject(response);
        String url = json.getString("url");
        logger.println(url);
        
        return ResponseEntity.ok().build();
    }
    
    private File convertToFile(MultipartFile multipartFile) throws IOException {
    File file = new File(System.getProperty("java.io.tmpdir"), multipartFile.getOriginalFilename());
    try (FileOutputStream outputStream = new FileOutputStream(file)) {
        outputStream.write(multipartFile.getBytes());
    }
    return file;
}



}