package Pacote;

import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Grafo<T> {
    private ArrayList<Vertice<T>> vertices;
    private ArrayList<Aresta<T>> arestas;

    public Grafo() {
        this.arestas = new ArrayList<Aresta<T>>();
        this.vertices = new ArrayList<Vertice<T>>();
    }

    public void adicionarVertice(Perfil dado) {
        Vertice<T> novoVertice = new Vertice<T>(dado);
        this.vertices.add(novoVertice);
    }

    public Vertice<T> getVertice(Perfil dado) {
        Vertice<T> vertice = null;
        for (int i = 0; i < this.vertices.size(); i++) {
            if (this.vertices.get(i).getDado().equals(dado)) {
                vertice = this.vertices.get(i);
                break;
            }
        }
        return vertice;
    }

    public void adicionarAresta(Perfil dadoInicio, Perfil dadoFim) {
        Vertice<T> fim = this.getVertice(dadoFim);
        Vertice<T> inicio = this.getVertice(dadoInicio);
        int peso = calcularPeso(dadoInicio, dadoFim);
        Aresta<T> aresta = new Aresta<T>(peso, inicio, fim);
        inicio.adicionarArestaSaida(aresta);
        fim.adicionarArestaEntrada(aresta);

        Aresta<T> arestaVolta = new Aresta<T>(peso, fim, inicio);
        fim.adicionarArestaSaida(arestaVolta);
        inicio.adicionarArestaEntrada(arestaVolta);

        this.arestas.add(aresta);
        this.arestas.add(arestaVolta);
    }

    public int calcularPeso(Perfil inicio, Perfil fim) {
        int peso = 0;
        Perfil verticeInicio = this.getVertice(inicio).getDado();
        Perfil verticeFim = this.getVertice(fim).getDado();
        if (verticeInicio.getNome().equalsIgnoreCase(verticeFim.getNome())) {
            peso++;
        }
        if (verticeInicio.getGenero().equalsIgnoreCase(verticeFim.getGenero())) {
            peso++;
        }
        if (verticeInicio.getIdade() == verticeFim.getIdade()) {
            peso++;
        }
        if (verticeInicio.getCorCabelo().equals(verticeFim.getCorCabelo())) {
            peso++;
        }
        if (verticeInicio.getLocal().equalsIgnoreCase(verticeFim.getLocal())) {
            peso++;
        }
        return peso;
    }

    public void buscaEmLargura() {
        ArrayList<Vertice<T>> marcados = new ArrayList<Vertice<T>>();
        ArrayList<Vertice<T>> fila = new ArrayList<Vertice<T>>();
        Vertice<T> atual = this.vertices.get(0);
        marcados.add(atual);
        System.out.println(atual.getDado());
        fila.add(atual);
        while (fila.size() > 0) {
            Vertice<T> visitado = fila.get(0);
            for (int i = 0; i < visitado.getArestaSaida().size(); i++) {
                Vertice<T> proximo = visitado.getArestaSaida().get(i).getFim();
                if (!marcados.contains(proximo)) {
                    marcados.add(proximo);
                    System.out.println(proximo.getDado());
                    fila.add(proximo);
                }
            }
            fila.remove(0);
        }
    }

    public Perfil editarVertice(Perfil filme) throws Exception {
        Vertice<T> vertice = this.getVertice(filme);
        if (vertices.contains(vertice)) {
            int i = vertices.indexOf(vertice);
            String nome = JOptionPane.showInputDialog("Informe o nome");
            String genero = JOptionPane.showInputDialog("Informe o gênero");
            String corCabelo = JOptionPane.showInputDialog("Informe a cor do cabelo");
            String local = JOptionPane.showInputDialog("Informe o local");
            String ft = JOptionPane.showInputDialog("Informe a url da imagem");
            int idade = Integer.parseInt(JOptionPane.showInputDialog("Informe a idade"));
            Perfil novoPerfil = new Perfil(nome, genero, corCabelo, local, ft, idade);
            vertices.get(i).setDado(novoPerfil);
            return novoPerfil;
        } else {
            throw new Exception("Não encontrado");
        }
    }

    public Perfil editarVertice(Perfil filme, int opcao) throws Exception {
        Vertice<T> vertice = this.getVertice(filme);
        int i = vertices.indexOf(vertice);
        if (vertices.contains(vertice)) {
            switch (opcao) {
                case 1:
                    vertices.get(i).getDado().setNome(JOptionPane.showInputDialog("Informe o nome"));
                    break;
                case 2:
                    vertices.get(i).getDado().setGenero(JOptionPane.showInputDialog("Informe o gênero"));
                    break;
                case 3:
                    vertices.get(i).getDado().setCorCabelo(JOptionPane.showInputDialog("Informe a cor do cabelo"));
                    break;
                case 4:
                    vertices.get(i).getDado().setLocal(JOptionPane.showInputDialog("Informe o local"));
                    break;
                case 5:
                    vertices.get(i).getDado().setFt(JOptionPane.showInputDialog("Informe a url da imagem"));
                    break;
                case 6:
                    int idade = Integer.parseInt(JOptionPane.showInputDialog("Informe a idade"));
                    vertices.get(i).getDado().setIdade(idade);
                    break;
                default:
                    throw new Exception("Opção inválida");
            }
        } else {
            throw new Exception("Filme não encontrado");
        }
        Perfil filmeEditado = vertices.get(i).getDado();
        return filmeEditado;
    }

    public ArrayList<Perfil> buscaEmLarguraArray(Perfil dado) {
        ArrayList<Vertice<T>> marcados = new ArrayList<Vertice<T>>();
        ArrayList<Vertice<T>> fila = new ArrayList<Vertice<T>>();
        ArrayList<Perfil> perfis = new ArrayList<Perfil>();
        perfis.clear();
        Vertice<T> atual = this.vertices.get(this.vertices.indexOf(this.getVertice(dado)));
        marcados.add(atual);
        perfis.add(atual.getDado());
        fila.add(atual);
        while (fila.size() > 0) {
            Vertice<T> visitado = fila.get(0);
            for (int i = 0; i < visitado.getArestaSaida().size(); i++) {
                Vertice<T> proximo = visitado.getArestaSaida().get(i).getFim();
                if (!marcados.contains(proximo) && proximo.isAtivo()) {
                    marcados.add(proximo);
                    perfis.add(proximo.getDado());
                    fila.add(proximo);
                }
            }
            fila.remove(0);
        }
        return perfis;
    }

    public void removerVertice(Perfil filme) {
        Vertice<T> vertice = this.getVertice(filme);
        if (this.vertices.contains(vertice)) {
            int i = this.vertices.indexOf(vertice);
            this.vertices.get(i).setAtivo(false);
            this.vertices.remove(vertice);
        }
    }

    public void removeAresta(Perfil source) {
        Vertice<T> vertice = this.getVertice(source);
        ArrayList<Aresta<T>> arestasRemover = new ArrayList<Aresta<T>>();
        for (int i = 0; i < this.arestas.size(); i++) {
            if (arestas.get(i).getInicio().equals(vertice) || arestas.get(i).getFim().equals(vertice)) {
                arestasRemover.add(arestas.get(i));
            }
        }
        if (arestasRemover != null) {
            for (int i = 0; i < arestasRemover.size(); i++) {
                arestas.remove(arestasRemover.get(i));
            }
        }
    }
}
