package by.it.group351051.вырко.lesson13;

import java.util.*;

public class GraphB {

    public static void main(String[] args) {
        // Считываем строку структуры орграфа
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        // Разбираем строку и строим граф
        Map<Integer, List<Integer>> graph = new HashMap<>();

        // Разбираем каждый элемент строки
        String[] edges = input.split(", ");
        for (String edge : edges) {
            String[] parts = edge.split(" -> ");
            int u = Integer.parseInt(parts[0]);
            int v = Integer.parseInt(parts[1]);

            graph.putIfAbsent(u, new ArrayList<>());
            graph.get(u).add(v);
        }

        // Проверяем наличие цикла в графе
        if (hasCycle(graph)) {
            System.out.println("yes");
        } else {
            System.out.println("no");
        }
    }

    // Метод для проверки наличия цикла в графе с использованием DFS
    public static boolean hasCycle(Map<Integer, List<Integer>> graph) {
        Set<Integer> visited = new HashSet<>();
        Set<Integer> recStack = new HashSet<>();  // Стек для отслеживания текущего пути (для поиска цикла)

        // Проходим по всем вершинам графа
        for (Integer node : graph.keySet()) {
            if (dfs(graph, node, visited, recStack)) {
                return true;  // Если цикл найден
            }
        }

        return false;  // Цикл не найден
    }

    // Вспомогательный метод DFS для поиска цикла
    private static boolean dfs(Map<Integer, List<Integer>> graph, int node, Set<Integer> visited, Set<Integer> recStack) {
        // Если вершина уже в стеке вызова, значит цикл найден
        if (recStack.contains(node)) {
            return true;
        }

        // Если вершина уже посещена, то просто пропускаем её
        if (visited.contains(node)) {
            return false;
        }

        // Помечаем вершину как посещенную и добавляем её в стек
        visited.add(node);
        recStack.add(node);

        // Рекурсивно проходим по всем соседям вершины
        if (graph.containsKey(node)) {
            for (int neighbor : graph.get(node)) {
                if (dfs(graph, neighbor, visited, recStack)) {
                    return true;
                }
            }
        }

        // Убираем вершину из стека (обратный ход)
        recStack.remove(node);
        return false;
    }
}