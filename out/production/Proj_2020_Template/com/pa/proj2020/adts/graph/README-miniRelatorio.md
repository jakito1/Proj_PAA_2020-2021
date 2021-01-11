# Mini Relatório do Projeto de Programação Avançada

O presente relatório aborda a estrutura de dados selecionada, os algoritmos de carregamento de dados e a construção do
modelo.

#### Ponto1: Estrutura de dados selecionada

- A estrutura de dados escolhida foi a estrutura em lista de adjacências

#### Ponto2: Algoritmos de carregamento de dados

- Foi criada uma classe ReadData responsável pelo carregamento dos dados dos ficheiros. O método readData(String
  filename) é responsável por ir buscar o ficheiro com o nome passado por argumento, ler cada linha do ficheiro e
  inserir os elementos num HashMap<String, ArrayList<String>> onde a key é o primeiro elelmento de cada linha e os
  values os elementos a seguir.

#### Ponto3: Construção do modelo

- O grafo é construido na classe SocialNetwork onde existem dois métodos initializeData() que irá ler cada ficheiro
  através método readData(String filename)
  da classe ReadData onde irá inserir os valores nos atributos correspondentes e o método constructModel() que irá
  inserir primeiro os vertices no grafo e depois inserir as arestas no mesmo. O grafo é visualizado com recurso à
  biblioteca grafica SmarthGraph disponibilizada durante as aulas teóricas

#### Nota: Versões

- Para a realização deste projeto, foram utilizados os ficheiros de javafx versão 15.0.1 e seguidos todos os passos do
  ficheiro PDF disponibilizado via moodle intitulado "Javafx Intellij Guide".
