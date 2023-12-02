package Model;

public class Cliente {
    private String Nome;
    private String RG;
    private String Passaporte;
    private String CPF;

    public Cliente() {
        
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String Nome) {
        this.Nome = Nome;
    }

    public String getRG() {
        return RG;
    }

    public void setRG(String RG) {
        this.RG = RG;
    }

    public String getPassaporte() {
        return Passaporte;
    }

    public void setPassaporte(String Passaporte) {
        this.Passaporte = Passaporte;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }
}
