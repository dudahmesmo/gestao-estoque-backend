package br.unisul.a3sdm.gestao_estoque_backend.model;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "amigo")
public class Amigo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String telefone;

    private String email;

    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;

    @Column(name = "odevedor")
    private Boolean ODevedor = false;
    
    @OneToMany(mappedBy = "amigo", cascade = CascadeType.ALL)
    @JsonManagedReference("emprestimos-amigo") 
    private List<Emprestimo> emprestimos;

    public Amigo() {}

    public Amigo(Long id, String nome, String telefone, String email) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.dataCadastro = LocalDateTime.now();
        this.ODevedor = false;
    }

    // GETTERS E SETTERS

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }
    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Boolean getODevedor() {
        return ODevedor;
    }
    public void setODevedor(Boolean oDevedor) {
        this.ODevedor = (oDevedor != null) ? oDevedor : false;
    }
    
    public List<Emprestimo> getEmprestimos() {
        return emprestimos;
    }

    public void setEmprestimos(List<Emprestimo> emprestimos) {
        this.emprestimos = emprestimos;
    }
}