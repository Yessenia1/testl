package pe.edu.upeu.sysalmacen.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UsuarioDTO {
    private Long idUsuario;

    @NotNull
    private String user;

    @NotNull
    private String estado;
    
    private String token;

    public record CredencialesDto(String user, char[] clave) {

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof CredencialesDto that)) return false;
            return Objects.equals(user, that.user) && Arrays.equals(clave, that.clave);
        }

        @Override
        public int hashCode() {
            int result = Objects.hash(user);
            result = 31 * result + Arrays.hashCode(clave);
            return result;
        }

        @Override
        public String toString() {
            return "CredencialesDto{" +
                   "user='" + user + '\'' +
                   ", clave=" + Arrays.toString(clave) +
                   '}';
        }
    }

    public record UsuarioCrearDto(String user, char[] clave, String rol, String estado) {

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof UsuarioCrearDto that)) return false;
            return Objects.equals(user, that.user)
                && Arrays.equals(clave, that.clave)
                && Objects.equals(rol, that.rol)
                && Objects.equals(estado, that.estado);
        }

        @Override
        public int hashCode() {
            int result = Objects.hash(user, rol, estado);
            result = 31 * result + Arrays.hashCode(clave);
            return result;
        }

        @Override
        public String toString() {
            return "UsuarioCrearDto{" +
                   "user='" + user + '\'' +
                   ", clave=" + Arrays.toString(clave) +
                   ", rol='" + rol + '\'' +
                   ", estado='" + estado + '\'' +
                   '}';
        }
    }
}
