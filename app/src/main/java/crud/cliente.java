package crud;

public class cliente {
    String idCliente;
    String nombre;
    String primerAp;
    String segundoAp;
    String sexo;
    String fechaNac;
    String correo;
    String numTel;

    public cliente(String idCliente, String nombre, String primerAp, String segundoAp, String sexo, String fechaNac, String correo, String numTel) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.primerAp = primerAp;
        this.segundoAp = segundoAp;
        this.sexo = sexo;
        this.fechaNac = fechaNac;
        this.correo = correo;
        this.numTel = numTel;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrimerAp() {
        return primerAp;
    }

    public void setPrimerAp(String primerAp) {
        this.primerAp = primerAp;
    }

    public String getSegundoAp() {
        return segundoAp;
    }

    public void setSegundoAp(String segundoAp) {
        this.segundoAp = segundoAp;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(String fechaNac) {
        this.fechaNac = fechaNac;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNumTel() {
        return numTel;
    }

    public void setNumTel(String numTel) {
        this.numTel = numTel;
    }
}
