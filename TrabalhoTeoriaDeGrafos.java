import java.util.*;

class Grafo {
    private int[][] matriz; 
    private int n;          

    public Grafo(int[][] matriz) {
        this.matriz = matriz;
        this.n = matriz.length;
    }

    public String tipoDoGrafo() {
        boolean dirigido = false;
        boolean multigrafo = false;
        boolean completo = true;
        boolean nulo = true;
        boolean regular = true;

        int grauEsperado = -1;

        for (int i = 0; i < n; i++) {
            int grau = 0;
            for (int j = 0; j < n; j++) {
                if (matriz[i][j] != matriz[j][i]) {
                    dirigido = true;
                }
                if (matriz[i][j] > 1) {
                    multigrafo = true;
                }
                if (i == j && matriz[i][j] > 0) {
                    multigrafo = true; 
                }
                if (matriz[i][j] > 0) {
                    nulo = false;
                }
                grau += matriz[i][j];
            }
            if (grauEsperado == -1) grauEsperado = grau;
            else if (grauEsperado != grau) regular = false;
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j && matriz[i][j] == 0) {
                    completo = false;
                }
            }
        }

        StringBuilder tipo = new StringBuilder();
        tipo.append(dirigido ? "Dirigido, " : "Não-dirigido, ");
        tipo.append(multigrafo ? "Multigrafo, " : "Simples, ");
        tipo.append(regular ? "Regular, " : "Não regular, ");
        tipo.append(completo ? "Completo, " : "Não completo, ");
        tipo.append(nulo ? "Nulo" : "Não nulo");

        return tipo.toString();
    }

    public String arestasDoGrafo() {
        List<String> arestas = new ArrayList<>();
        int qtd = 0;

        for (int i = 0; i < n; i++) {
            for (int j = (i + 1); j < n; j++) { 
                for (int k = 0; k < matriz[i][j]; k++) {
                    arestas.add("(" + i + "," + j + ")");
                    qtd++;
                }
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j && matriz[i][j] > 0 && matriz[j][i] == 0) {
                    for (int k = 0; k < matriz[i][j]; k++) {
                        arestas.add("(" + i + "→" + j + ")");
                        qtd++;
                    }
                }
            }
        }

        return "Quantidade de arestas: " + qtd + "\nArestas: " + arestas;
    }

    public String grausDoVertice() {
        int[] graus = new int[n];
        int grauMax = 0;

        for (int i = 0; i < n; i++) {
            int grau = 0;
            for (int j = 0; j < n; j++) {
                grau += matriz[i][j];
            }
            graus[i] = grau;
            if (grau > grauMax) grauMax = grau;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Grau máximo do grafo: ").append(grauMax).append("\n");
        for (int i = 0; i < n; i++) {
            sb.append("Vértice ").append(i).append(" -> grau: ").append(graus[i]).append("\n");
        }
        sb.append("Sequência de graus: ").append(Arrays.toString(graus));

        return sb.toString();
    }

    public String buscaEmProfundidade() {
        boolean[] visitado = new boolean[n];
        List<Integer> ordem = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            if (!visitado[i]) {
                dfs(i, visitado, ordem);
            }
        }

        return "Ordem de visita (DFS): " + ordem;
    }

    private void dfs(int v, boolean[] visitado, List<Integer> ordem) {
        visitado[v] = true;
        ordem.add(v);
        for (int j = 0; j < n; j++) {
            if (matriz[v][j] > 0 && !visitado[j]) {
                dfs(j, visitado, ordem);
            }
        }
    }
}

public class App {
    public static void main(String[] args) {
        int[][] matriz = {
            {0,1,1},
            {1,0,1},
            {1,1,0}
        };

        Grafo g = new Grafo(matriz);

        System.out.println("1) Tipo do grafo:");
        System.out.println(g.tipoDoGrafo());
        System.out.println();

        System.out.println("2) Arestas do grafo:");
        System.out.println(g.arestasDoGrafo());
        System.out.println();

        System.out.println("3) Graus dos vértices:");
        System.out.println(g.grausDoVertice());
        System.out.println();

        System.out.println("4) Busca em profundidade:");
        System.out.println(g.buscaEmProfundidade());
    }
}
