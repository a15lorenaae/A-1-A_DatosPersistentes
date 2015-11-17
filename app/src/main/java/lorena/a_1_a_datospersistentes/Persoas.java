package lorena.a_1_a_datospersistentes;

/**
 * Created by Lorena on 14/11/2015.
 */
public class Persoas {
    private String nome;
    private String descricion;

    public Persoas(String nome, String descricion) {
        this.nome = nome;
        this.descricion = descricion;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricion() {
        return descricion;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricion(String descricion) {
        this.descricion = descricion;
    }

    @Override
    public String toString() {
        return nome;
    }
}
