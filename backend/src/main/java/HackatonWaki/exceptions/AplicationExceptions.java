package HackatonWaki.exceptions;

public class AplicationExceptions extends RuntimeException{

    private String campo;

    public AplicationExceptions(String mensaje) {
        super(mensaje);
    }

    public AplicationExceptions(String campo, String mensaje) {
        super(mensaje);
        this.campo = campo;
    }

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }

}
