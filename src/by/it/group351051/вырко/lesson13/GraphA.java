package by.it.group351051.вырко.lesson13;

import java.util.*;

public class GraphA {

    // Метод для выполнения топологической сортировки
    public static void main(String[] args) {
        // Считываем строку структуры орграфа
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        // Разбираем строку и строим граф
        Map<String, List<String>> graph = new HashMap<>();
        Map<String, Integer> inDegree = new HashMap<>();

        // Разбираем каждый элемент строки
        String[] edges = input.split(", ");
        for (String edge : edges) {
            String[] parts = edge.split(" -> ");
            String u = parts[0];
            String v = parts[1];

            // Строим граф (связанности) и подсчитываем степень входа
            graph.putIfAbsent(u, new ArrayList<>());
            graph.get(u).add(v);

            inDegree.put(u, inDegree.getOrDefault(u, 0));
            inDegree.put(v, inDegree.getOrDefault(v, 0) + 1);
        }

        // Строим топологическую сортировку с учетом лексикографического порядка
        topologicalSort(graph, inDegree);
    }

    // Метод для топологической сортировки
    public static void topologicalSort(Map<String, List<String>> graph, Map<String, Integer> inDegree) {
        // Используем очередь с приоритетом для лексикографического порядка
        PriorityQueue<String> queue = new PriorityQueue<>();

        // Добавляем все вершины с нулевой степенью входа в очередь
        for (String node : inDegree.keySet()) {
            if (inDegree.get(node) == 0) {
                queue.offer(node);
            }
        }

        // Результат топологической сортировки
        List<String> result = new ArrayList<>();

        while (!queue.isEmpty()) {
            // Берем вершину с наименьшим значением (лексикографически)
            String current = queue.poll();
            result.add(current);

            // Уменьшаем степень входа соседей
            if (graph.containsKey(current)) {
                for (String neighbor : graph.get(current)) {
                    inDegree.put(neighbor, inDegree.get(neighbor) - 1);
                    // Если степень входа соседа стала равной нулю, добавляем его в очередь
                    if (inDegree.get(neighbor) == 0) {
                        queue.offer(neighbor);
                    }
                }
            }
        }

        // Выводим результат
        for (String node : result) {
            System.out.print(node + " ");
        }
    }
}