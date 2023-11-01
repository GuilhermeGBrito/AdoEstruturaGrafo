package Pacote;

import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;


public class Interface<T> extends JFrame implements ActionListener{
	//Instanciando o grafo e a ArrayList que vai guardar os filmes
	Grafo<T> grafo = new Grafo<T>();
	ArrayList<Perfil> perfis = new ArrayList<Perfil>();
	//Instanciando variáveis globais que precisam ser tratadas durante o código
	  JMenuBar barraMenu;
	  JMenu principal, atualizar;
	  JMenuItem main, adicionar, editar,remover;
	  JPanel inicial, painelAdicionar, painelRemover, menu, listaPerfil, painelEditar;
	  JLabel recomendacao;
	  
	  //Método construtor que já implementa o JFrame
	public Interface() {
		
		//Adicionando alguns filmes
		Perfil Gabriela = new Perfil("Gabriela" , "Masculino" , "Amarelo", "NY", "https://cdn.jornaldebrasilia.com.br/wp-content/uploads/2023/08/17153641/Elisa-Sanches-fatura-100-mil-por-mes-com-conteudo-explicito-na-Privacy.jpg", 20);
		Perfil Roberta = new Perfil("Roberta" , "Masculino" , "Amarelo", "SP", "https://odia.ig.com.br/_midias/jpg/2019/01/15/mia10-9308889.jpg?20210527145641", 20);
		Perfil Camila = new Perfil("Camila" , "Masculino" , "Amarelo", "Bahia", "https://i.pinimg.com/736x/a5/cd/66/a5cd66fce51ea9b9245d550cdabfc885.jpg", 20);
		Perfil Maria = new Perfil("Maria" , "Masculino" , "Amarelo", "Bahia", "https://i.pinimg.com/736x/a1/95/ba/a195ba795cec175c9870a42be091316f.jpg", 20);
		perfis.add(Gabriela);
		perfis.add(Roberta);
		perfis.add(Camila);
		perfis.add(Maria);
		grafo.adicionarVertice(Gabriela);
		grafo.adicionarVertice(Roberta);
		grafo.adicionarVertice(Camila);
		grafo.adicionarVertice(Maria);
		//Adicionando relações entre eles
		grafo.adicionarAresta(Gabriela, Roberta);
		grafo.adicionarAresta(Roberta, Camila);
		grafo.adicionarAresta(Camila, Maria);
		grafo.adicionarAresta(Maria, Camila);
		grafo.adicionarAresta(Roberta, Gabriela);
		grafo.adicionarAresta(Camila, Maria);
		grafo.adicionarAresta(Maria, Gabriela);
		grafo.adicionarAresta(Maria, Maria);
		grafo.adicionarAresta(Maria, Roberta);
		inicial =  new JPanel();
		
		//Configurações básicas do JFrame
		inicial.setLayout(new BorderLayout());
		setTitle("Atividade aula reversa com grafos");
		setSize(2000,2000);
		getContentPane().setLayout(new BorderLayout());
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		getContentPane().setBackground(Color.lightGray);
		
		//Criação do painel que vai guardar o menu
		menu = new JPanel();
		menu.setLayout(new GridLayout(0,1));
		
		//Criando painel que ficará no meio
		listaPerfil = new JPanel();
		recomendacao = criarLabel("");
		recomendacao.setForeground(Color.blue);
		listaPerfil.setLayout(new FlowLayout());
		add(menu, BorderLayout.NORTH);
		//Adicionando eles no Frame
		inicial.add(listaPerfil, BorderLayout.CENTER);
		renderizarLista(perfis);
		

		menu.setBackground(new Color(208, 211, 212));
		criarMenu();
		menu.add(recomendacao);
		add(inicial);
		this.revalidate();
	}
	//Main apenas para chamar o construtor
	public static void main(String[] args) {
		new Interface();
		
		

	}
	//NEsta função será criado cada painel de filmes passando como parâmetro o filme
	public JPanel criarPanelFilme(Perfil perfil) {
		//Criado o painel
		JPanel painel = new JPanel();
		painel.setBorder(new LineBorder(Color.black));
		painel.setLayout(new GridLayout(0,1));
		painel.setAlignmentX(SwingConstants.CENTER);;
		painel.setBackground(Color.lightGray);
		
		//Criado paineis que ficarão dentro do painel principal
		JPanel um =criarPainelInfo();
		JPanel dois = criarPainelInfo();
		JPanel tres = criarPainelInfo();
		JPanel quatro = criarPainelInfo();
		JPanel cinco = criarPainelInfo();
		JPanel seis = criarPainelInfo();
		JPanel sete = criarPainelInfo();
		//Lendo a imagem da url
		try {
			BufferedImage bufferedImage = ImageIO.read(new URL(perfil.getFt()));
			Image image = bufferedImage.getScaledInstance(70, 80, Image.SCALE_DEFAULT);
			JLabel img = new JLabel(new ImageIcon(image));


			img.setSize(1,1);
			um.add(img);
		} catch (MalformedURLException e1) {
			
			e1.printStackTrace();
		} catch (IOException e1) {
			
			e1.printStackTrace();
		}
		
		
		//Criando os labels e botão
		JLabel nome = criarLabel("Nome: " +perfil.getNome());
		JLabel genero = criarLabel("Genero: "+ perfil.getGenero());
		JLabel corCabelo = criarLabel("Cabelo: "+ perfil.getCorCabelo());
		JLabel local = criarLabel("Local: "+ perfil.getLocal());
		JLabel idade = criarLabel("Idade: "+ perfil.getIdade());
		
		JButton like = new JButton("Gostei");
		like.setBackground(Color.green);
		
		//Adicionando listener de evento ao clicar no botão
		like.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				novaPágina(perfil);
				
			}

			private void novaPágina(Perfil perfil) {
				//Criar uma nova ArrayList para armazenar os filmes que vai receber da função buscaEmLarguraArray
				ArrayList<Perfil> filmesRecomendados = grafo.buscaEmLarguraArray(perfil);
				recomendacao.setText("Recomendações");
				recomendacao.setForeground(Color.red);
				//Limpa o Painel para não concatenar com os outros
				listaPerfil.removeAll();
				//Tira o filme que foi selecionado
				filmesRecomendados.remove(0);
				//Checa se a lista de filmes recomendados está vazia
				if(filmesRecomendados.size() ==0) {
					recomendacao.setText("Nenhuma recomendação");
					recomendacao.setForeground(Color.red);
				}
				//Renderiza a tela com os filmes recomendadods
				renderizarLista(filmesRecomendados);
				getContentPane().revalidate();
				getContentPane().repaint();
			}
		});
		
		//Adicionando os lables aos seus paineis
		dois.add(nome);
		tres.add(genero);
		quatro.add(idade);
		cinco.add(local);
		seis.add(corCabelo);
		sete.add(like);
		
		//Adiciona os paineis ao painel principal
		painel.add(um);
		painel.add(dois);
		painel.add(tres);
		painel.add(quatro);
		painel.add(cinco);
		painel.add(seis);
		painel.add(sete);
		
		return painel;
	}
	
	public void criarMenu() {
		//Adiciona as opções no menu
		barraMenu = new JMenuBar();
		principal = new JMenu("Principal");
		atualizar = new JMenu("Atualizar");
		main = new JMenuItem("Main");
		adicionar = new JMenuItem("Adicionar");
		remover = new JMenuItem("Remover");
		editar  = new JMenuItem("Editar");
		//Adiciona os tratamento de eventos
		adicionar.addActionListener(this);
		main.addActionListener(this);
		remover.addActionListener(this);
		editar.addActionListener(this);
		//Adiciona no menu as opções
		barraMenu.add(principal);
		barraMenu.add(atualizar);
		principal.add(main);
		atualizar.add(adicionar);
		atualizar.add(editar);
		atualizar.add(remover);
		//Adiciona no frame
		menu.add(barraMenu);
	}
	
	public JLabel criarLabel(String texto) {
		
		//Função criada para criar labels de maneira padronizada
		JLabel label = new JLabel(texto);

		label.setHorizontalAlignment(SwingConstants.CENTER);
		
		return label;
	}
	
	public void renderizarLista(ArrayList<Perfil> perfilNovo) {
		//remove todos os outros filmes previamente renderizados
		listaPerfil.removeAll();
		//Laço de repetição feito para renderizar cada filme na tela
		for (int i = 0; i < perfilNovo.size(); i++) {
			Perfil perfil = perfilNovo.get(i);
			listaPerfil.add(criarPanelFilme(perfil));
		}
	}
	//Tratamneto de eventos
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == adicionar) {
			paginaAdicionar();
		}
		if(e.getSource() == main) {
			paginaInicial();
		}
		if(e.getSource() == remover) {
			paginaRemover();
		}
		if(e.getSource() == editar) {
			paginaEditar();
		}
	}
	
	private void paginaEditar() {
		//Remove outras telas
		this.limparTela();
		//Adiciona e renderiza a página de editar
		painelEditar = new JPanel();
		painelEditar.setLayout(new BorderLayout());
		renderizarPaginaEditar(perfis);
		recomendacao.setText("Editar");
		recomendacao.setForeground(Color.blue);
		add(painelEditar, BorderLayout.CENTER);
		this.revalidate();
		this.repaint();
		
	}

	private void renderizarPaginaEditar(ArrayList<Perfil> perfilNovo) {
		painelEditar.removeAll();
		JPanel novoPanel = new JPanel();
		novoPanel.removeAll();
		for (int i = 0; i < perfilNovo.size(); i++) {
			Perfil filme = perfilNovo.get(i);
			novoPanel.add(criarPanelFilmeEditar(filme));
		}
		painelEditar.add(novoPanel);
		
	}

	private Component criarPanelFilmeEditar(Perfil perfil) {
		JPanel painel = new JPanel();
		painel.setBorder(new LineBorder(Color.black));
		painel.setLayout(new GridLayout(0,1));
		painel.setAlignmentX(SwingConstants.CENTER);;
		painel.setBackground(Color.lightGray);
		
		JPanel um =criarPainelInfo();
		JPanel dois = criarPainelInfo();
		JPanel tres = criarPainelInfo();
		JPanel quatro = criarPainelInfo();
		JPanel cinco = criarPainelInfo();
		JPanel seis = criarPainelInfo();
		JPanel sete = criarPainelInfo();
		try {
			BufferedImage bufferedImage = ImageIO.read(new URL(perfil.getFt()));
			Image image = bufferedImage.getScaledInstance(70, 80, Image.SCALE_DEFAULT);
			JLabel img = new JLabel(new ImageIcon(image));


			img.setSize(1,1);
			um.add(img);
		} catch (MalformedURLException e1) {
			
			e1.printStackTrace();
		} catch (IOException e1) {
			
			e1.printStackTrace();
		}
		
		
		
		JLabel nome = criarLabel("Nome: " +perfil.getNome());
		JLabel genero = criarLabel("Genero: "+ perfil.getGenero());
		JLabel idade = criarLabel("Diretor: "+perfil.getIdade());
		JLabel corCabelo = criarLabel("Ano: " +perfil.getCorCabelo());
		JLabel local = criarLabel("Ano: " +perfil.getLocal());
		JButton like = new JButton("Editar");
		like.setBackground(Color.blue);
		like.setForeground(Color.white);
		like.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//Confirma se quer editar
				int reply = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja editar?:\n " + perfil.toString(),"?", JOptionPane.YES_NO_OPTION);
				if (reply == JOptionPane.YES_OPTION) {
					//Pergunta o que quer alterar
				    int opcao = Integer.parseInt(JOptionPane.showInputDialog("O que deseja fazer? \n"
				    		+ "[1] Alterar nome \n"
				    		+ "[2] Alterar gênero \n"
				    		+ "[3] Alterar idade \n"
				    		+ "[4] Alterar corCabelo \n"
				    		+ "[5] Alterar local \n"
				    		+ "[6] Alterar url da Imagem \n"
				    		+ "[7] Alterar tudo"));
				    switch(opcao) {
				    //Caso for 7, é preciso alterar tudo
				    case 7:
				    	try {
				    		int i = perfis.indexOf(perfil);
				    		
				    		Perfil novoFilme = grafo.editarVertice(perfil);
				    		perfis.set(i, novoFilme);
						} catch (Exception e1) {
							recomendacao.setText("Filme inválido");
						}
				    	break;
				    	//Caso contrário, passa a opção e altera apenas o selecionado
				    default:
				    	try {
				    		int i = perfis.indexOf(perfil);
				    		Perfil novoFilme = grafo.editarVertice(perfil, opcao);
				    		perfis.set(i, novoFilme);
						} catch (Exception e1) {
							recomendacao.setText("Valor inválido");
						}
				    }
				    
				    limparTela();
				    renderizarPaginaEditar(perfis);
				    getContentPane().add(painelEditar, BorderLayout.CENTER);
				    getContentPane().revalidate();
				    getContentPane().repaint();
				}
			}

			
		});
		dois.add(nome);
		tres.add(genero);
		quatro.add(idade);
		cinco.add(local);
		seis.add(corCabelo);
		sete.add(like);
		painel.add(um);
		painel.add(dois);
		painel.add(tres);
		painel.add(quatro);
		painel.add(cinco);
		painel.add(seis);
		painel.add(sete);
		
		return painel;
	}

	private void paginaRemover() {
		this.limparTela();
		painelRemover = new JPanel();
		painelRemover.setLayout(new BorderLayout());
		renderizarPaginaRemover(perfis);
		recomendacao.setText("Remover");
		recomendacao.setForeground(Color.red);
		add(painelRemover, BorderLayout.CENTER);
		this.revalidate();
		this.repaint();
	}

	private void renderizarPaginaRemover(ArrayList<Perfil> perfilNovo) {
		painelRemover.removeAll();
		JPanel novoPanel = new JPanel();
		novoPanel.removeAll();
		for (int i = 0; i < perfilNovo.size(); i++) {
			Perfil perfil = perfilNovo.get(i);
			novoPanel.add(criarPanelFilmeRemover(perfil));
		}
		painelRemover.add(novoPanel);
	}

	private Component criarPanelFilmeRemover(Perfil perfil) {
		JPanel painel = new JPanel();
		painel.setBorder(new LineBorder(Color.black));
		painel.setLayout(new GridLayout(0,1));
		painel.setAlignmentX(SwingConstants.CENTER);;
		painel.setBackground(Color.lightGray);
		
		JPanel um =criarPainelInfo();
		JPanel dois = criarPainelInfo();
		JPanel tres = criarPainelInfo();
		JPanel quatro = criarPainelInfo();
		JPanel cinco = criarPainelInfo();
		JPanel seis = criarPainelInfo();
		JPanel sete = criarPainelInfo();
		try {
			BufferedImage bufferedImage = ImageIO.read(new URL(perfil.getFt()));
			Image image = bufferedImage.getScaledInstance(70, 80, Image.SCALE_DEFAULT);
			JLabel img = new JLabel(new ImageIcon(image));


			img.setSize(1,1);
			um.add(img);
		} catch (MalformedURLException e1) {
			
			e1.printStackTrace();
		} catch (IOException e1) {
			
			e1.printStackTrace();
		}
		
		
		
		JLabel nome = criarLabel("Nome: " +perfil.getNome());
		JLabel genero = criarLabel("Genero: "+ perfil.getGenero());
		JLabel idade = criarLabel("Idade: "+perfil.getIdade());
		JLabel corCabelo = criarLabel("Cor Cabelo: " +perfil.getCorCabelo());
		JLabel local = criarLabel("Local: " +perfil.getLocal());
		
		JButton like = new JButton("Remover");
		like.setBackground(Color.red);
		like.setForeground(Color.white);
		like.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//Confirma se quer remover
				int reply = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover:\n " + perfil.toString(),"?", JOptionPane.YES_NO_OPTION);
				if (reply == JOptionPane.YES_OPTION) {
					//remove da arraylist e do grafo
				    perfis.remove(perfil);
				    //remove todas as arestas relacionadas a este filme
				    grafo.removeAresta(perfil);
				    grafo.removerVertice(perfil);
				    limparTela();
				    renderizarPaginaRemover(perfis);
				    getContentPane().add(painelRemover, BorderLayout.CENTER);
				    getContentPane().revalidate();
				    getContentPane().repaint();
				}
			}

			
		});
		dois.add(nome);
		tres.add(genero);
		quatro.add(idade);
		cinco.add(local);
		seis.add(corCabelo);
		sete.add(like);
		painel.add(um);
		painel.add(dois);
		painel.add(tres);
		painel.add(quatro);
		painel.add(cinco);
		painel.add(seis);
		painel.add(sete);
		
		return painel;
	}

	private void limparTela() {
		//Limpa a parte central da tela
		try {
			this.remove(painelAdicionar);
		}catch(Exception e) {
			
		}try {
			this.remove(inicial);
		}catch(Exception e) {
			
		}try {
			this.remove(painelRemover);
		}catch(Exception e) {
			
		}try {
			this.remove(painelEditar);
		}catch(Exception e) {
			
		}
		this.revalidate();
		this.repaint();
	}
	
	
	private void paginaInicial() {
		this.limparTela();
		this.renderizarLista(perfis);
		recomendacao.setText("");
		this.add(inicial);
		this.revalidate();
		this.repaint();
		
	}

	private void paginaAdicionar() {
		this.limparTela();
		renderizarPaginaAdicionar();
		this.revalidate();
		this.repaint();
		
	}

	private void renderizarPaginaAdicionar() {
		painelAdicionar = new JPanel();
		JPanel botao = new JPanel();
		JPanel inputs = new JPanel();
		painelAdicionar.setLayout(new GridLayout(0,1));
		inputs.setBackground(Color.LIGHT_GRAY);
		recomendacao.setText("Adicionar novo filme");
		recomendacao.setForeground(Color.BLUE);
		inputs.setLayout(new GridLayout(0,2));
		JLabel nomeLabel = criarLabel("Nome");
		JLabel idadeLabel = criarLabel("Idade");
		JLabel generoLabel = criarLabel("Genero");
		JLabel localLabel = criarLabel("Local: ");
		JLabel corCabeloLabel = criarLabel("Cabelo: ");
		JLabel urlLabel = criarLabel("URL da imagem");
		
		JTextField nomeInput = criarInput();
		JTextField idadeInput = criarInput();
		JTextField generoInput = criarInput();
		JTextField localInput = criarInput();
		JTextField corDoCabeloInput = criarInput();
		JTextField urlInput = criarInput();
		
		inputs.add(criarPainelLables(painelAdicionar,nomeLabel,nomeInput));
		inputs.add(criarPainelLables(painelAdicionar,idadeLabel,idadeInput));
		inputs.add(criarPainelLables(painelAdicionar,generoLabel,generoInput));
		inputs.add(criarPainelLables(painelAdicionar,localLabel,localInput));
		inputs.add(criarPainelLables(painelAdicionar,corCabeloLabel,corDoCabeloInput));
		inputs.add(criarPainelLables(painelAdicionar,urlLabel,urlInput));
		
		JButton adicionar = new JButton("Adicionar");
		
		adicionar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == adicionar) {
					
					Perfil novo = new Perfil(nomeInput.getText(), idadeInput.getText(), generoInput.getText(),urlInput.getText(),localInput.getText(),Integer.parseInt(corDoCabeloInput.getText()));
					
					int decisao =0;
					do {
						decisao = Integer.parseInt(JOptionPane.showInputDialog(listaFilmesIndex()));
						decisao -= 1;
						if(decisao == -1) {
							break;
						}else {
							
							grafo.adicionarVertice(novo);
							grafo.adicionarAresta(novo, perfis.get(decisao));
							
							
						}
					}while(decisao != -1);
					perfis.add(novo);
				}
				
			}
		});
		
		botao.add(adicionar);
		
		painelAdicionar.add(inputs);
		painelAdicionar.add(botao);
		add(painelAdicionar);
		this.revalidate();
		this.repaint();
	}
	
	private JPanel criarPainelLables(JPanel painel, JLabel label, JTextField Input) {
		JPanel novoPainel = new JPanel();
		novoPainel.setLayout(null);
		label.setBounds(40, 50, 100, 40);
		Input.setBounds(140, 50, 100, 30);

		novoPainel.add(label);
		novoPainel.add(Input);
		return novoPainel;
	}

	private JTextField criarInput() {
		JTextField texto = new JTextField();
		texto.setSize(1, 1);
		return texto;
	}
	
	
	private String listaFilmesIndex() {
		//Fazer uma string para usar na hora das relações
		StringBuilder builder = new StringBuilder();
		
		builder.append("Lista de filmes registrados: \n");
		
		for (int i = 0; i < perfis.size(); i++) {
			builder.append("[" + (i +1) +"] " + perfis.get(i).getNome()  +"\n");
		}
		
		builder.append("[0] Caso esse filme não tenha relação com nenhum outro");
		String texto = builder.toString();
		return texto;
	}
	
	private JPanel criarPainelInfo() {
		JPanel novo = new JPanel();
		novo.setBackground(Color.LIGHT_GRAY);

		return novo;
		
	}
}
