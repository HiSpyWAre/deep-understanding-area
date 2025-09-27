
// Nama : Nayyara Aqila Azra
// NIM  : L0124138
// Kelas: A
// Judul Program : Smart Backpacking Planner

import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

class Event implements Comparable<Event> {
    String title;
    LocalDate date;
    String location;
    String category;

    Event(String title, LocalDate date, String location, String category) {
        this.title = title;
        this.date = date;
        this.location = location;
        this.category = category;
    }

    @Override
    public int compareTo(Event other) {
        return this.date.compareTo(other.date);
    }

    @Override
    public String toString() {
        return title + " | " + date + " | " + location + " | " + category;
    }
}

class EventBST {
    class Node {
        Event data;
        Node left, right;

        Node(Event data) {
            this.data = data;
        }
    }

    private Node root;

    void insert(Event event) {
        root = insertRecursive(root, event);
    }

    private Node insertRecursive(Node node, Event event) {
        if (node == null)
            return new Node(event);
        if (event.compareTo(node.data) < 0)
            node.left = insertRecursive(node.left, event);
        else
            node.right = insertRecursive(node.right, event);
        return node;
    }

    void inOrderTraversal(List<Event> list) {
        inOrderRecursive(root, list);
    }

    private void inOrderRecursive(Node node, List<Event> list) {
        if (node != null) {
            inOrderRecursive(node.left, list);
            list.add(node.data);
            inOrderRecursive(node.right, list);
        }
    }

    List<Event> searchAllByDate(LocalDate date) {
        List<Event> result = new ArrayList<>();
        searchAllRecursive(root, date, result);
        return result;
    }

    private void searchAllRecursive(Node node, LocalDate date, List<Event> result) {
        if (node == null)
            return;
        if (date.isBefore(node.data.date)) {
            searchAllRecursive(node.left, date, result);
        } else if (date.isAfter(node.data.date)) {
            searchAllRecursive(node.right, date, result);
        } else {
            result.add(node.data);
            searchAllRecursive(node.left, date, result);
            searchAllRecursive(node.right, date, result);
        }
    }

    void clear() {
        root = null;
    }
}

class EventBinaryTree {
    class Node {
        Event data;
        Node left, right;

        Node(Event data) {
            this.data = data;
        }
    }

    private Node root;

    public void insert(Event event) {
        Node newNode = new Node(event);
        if (root == null) {
            root = newNode;
            return;
        }

        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            if (current.left == null) {
                current.left = newNode;
                return;
            } else
                queue.offer(current.left);
            if (current.right == null) {
                current.right = newNode;
                return;
            } else
                queue.offer(current.right);
        }
    }

    public void clear() {
        root = null;
    }

    public void preOrderTraversal() {
        System.out.println("Rencana Perjalanan dalam Binary Tree (PreOrder):");
        preOrderRecursive(root);
    }

    private void preOrderRecursive(Node node) {
        if (node != null) {
            System.out.println("- " + node.data);
            preOrderRecursive(node.left);
            preOrderRecursive(node.right);
        }
    }
}

// Edge class for MST and Shortest Path
class Edge implements Comparable<Edge> {
    String source, destination;
    int weight;

    Edge(String source, String destination, int weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    @Override
    public int compareTo(Edge other) {
        return Integer.compare(this.weight, other.weight);
    }

    @Override
    public String toString() {
        return source + " -> " + destination + " (cost: " + weight + ")";
    }
}

// Union-Find data structure for Kruskal's algorithm
class UnionFind {
    private Map<String, String> parent;
    private Map<String, Integer> rank;

    public UnionFind() {
        parent = new HashMap<>();
        rank = new HashMap<>();
    }

    public void makeSet(String x) {
        parent.put(x, x);
        rank.put(x, 0);
    }

    public String find(String x) {
        if (!parent.get(x).equals(x)) {
            parent.put(x, find(parent.get(x))); // Path compression
        }
        return parent.get(x);
    }

    public boolean union(String x, String y) {
        String rootX = find(x);
        String rootY = find(y);

        if (rootX.equals(rootY))
            return false;

        // Union by rank
        if (rank.get(rootX) < rank.get(rootY)) {
            parent.put(rootX, rootY);
        } else if (rank.get(rootX) > rank.get(rootY)) {
            parent.put(rootY, rootX);
        } else {
            parent.put(rootY, rootX);
            rank.put(rootX, rank.get(rootX) + 1);
        }
        return true;
    }
}

// Location Network Manager
class LocationNetworkManager {
    private List<Edge> edges;
    private Set<String> locations;
    private Map<String, Map<String, Integer>> adjacencyMatrix;

    public LocationNetworkManager() {
        edges = new ArrayList<>();
        locations = new HashSet<>();
        adjacencyMatrix = new HashMap<>();
    }

    public void addLocation(String location) {
        locations.add(location);
        adjacencyMatrix.putIfAbsent(location, new HashMap<>());
    }

    public void addConnection(String from, String to, int cost) {
        addLocation(from);
        addLocation(to);

        edges.add(new Edge(from, to, cost));

        // Add to adjacency matrix for shortest path
        adjacencyMatrix.get(from).put(to, cost);
        adjacencyMatrix.get(to).put(from, cost); // Undirected graph
    }

    // Kruskal's MST Algorithm
    public List<Edge> findMST() {
        List<Edge> mst = new ArrayList<>();
        Collections.sort(edges); // Sort edges by weight

        UnionFind uf = new UnionFind();
        for (String location : locations) {
            uf.makeSet(location);
        }

        for (Edge edge : edges) {
            if (uf.union(edge.source, edge.destination)) {
                mst.add(edge);
                if (mst.size() == locations.size() - 1)
                    break;
            }
        }

        return mst;
    }

    // Dijkstra's Shortest Path Algorithm
    public Map<String, Integer> shortestPath(String start) {
        Map<String, Integer> distances = new HashMap<>();
        Map<String, String> previous = new HashMap<>();
        PriorityQueue<String> pq = new PriorityQueue<>(
                Comparator.comparingInt(distances::get));

        // Initialize distances
        for (String location : locations) {
            distances.put(location, location.equals(start) ? 0 : Integer.MAX_VALUE);
        }

        pq.offer(start);

        while (!pq.isEmpty()) {
            String current = pq.poll();

            if (adjacencyMatrix.containsKey(current)) {
                for (Map.Entry<String, Integer> neighbor : adjacencyMatrix.get(current).entrySet()) {
                    String next = neighbor.getKey();
                    int weight = neighbor.getValue();
                    int newDist = distances.get(current) + weight;

                    if (newDist < distances.get(next)) {
                        distances.put(next, newDist);
                        previous.put(next, current);
                        pq.offer(next);
                    }
                }
            }
        }

        return distances;
    }

    public List<String> getPath(String start, String end) {
        Map<String, Integer> distances = shortestPath(start);
        List<String> path = new ArrayList<>();

        if (distances.get(end) == Integer.MAX_VALUE) {
            return path; // No path found
        }

        path.add(start);
        path.add(end);
        return path;
    }

    public Set<String> getLocations() {
        return new HashSet<>(locations);
    }

    public void initializeSampleNetwork() {
        // Sample network for demonstration
        addConnection("Jakarta", "Bandung", 150);
        addConnection("Jakarta", "Surabaya", 800);
        addConnection("Jakarta", "Yogyakarta", 560);
        addConnection("Bandung", "Yogyakarta", 350);
        addConnection("Bandung", "Semarang", 400);
        addConnection("Yogyakarta", "Surabaya", 320);
        addConnection("Semarang", "Surabaya", 310);
        addConnection("Medan", "Jakarta", 1400);
        addConnection("Makassar", "Surabaya", 600);
    }
}

public class PSDA_Responsi2_L0124138_NayyaraAqilaAzra {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            List<Event> allEvents = new ArrayList<>();
            Stack<Event> undoStack = new Stack<>();
            Stack<Event> redoStack = new Stack<>();
            Queue<Event> upcomingQueue = new LinkedList<>();
            Set<String> categories = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
            Map<String, List<Event>> locationMap = new HashMap<>();
            EventBST bst = new EventBST();
            EventBinaryTree binaryTree = new EventBinaryTree();
            LocationNetworkManager network = new LocationNetworkManager();

            // Initialize sample network
            network.initializeSampleNetwork();

            System.out.println("   =================================================");
            System.out.println("   |                WELCOME TO THE                  |");
            System.out.println("   |           SMART BACKPACKING PLANNER            |");
            System.out.println("   |  * organize your travel's plan better !!! *    |");
            System.out.println("   =================================================\n");

            while (true) {
                System.out.println("=========================================");
                System.out.println("1. Tambah Tujuam Perjalanan");
                System.out.println("2. Lihat Itinerary (Terurut Tanggal)");
                System.out.println("3. Cari Tempat Berdasarkan Tanggal Kunjungan");
                System.out.println("4. Batalkan Tujuan Terakhir");
                System.out.println("5. Pulihkan Tujuan Terakhir");
                System.out.println("6. Tambah ke Daftar Destinasi Selanjutnya ");
                System.out.println("7. Kunjungi Tujuan Berikutnya (Hapus dari antrian)");
                System.out.println("8. Lihat Kategori Tujuan Wisata");
                System.out.println("9. Lihat Destinasi Berdasarkan Kota/Negara");
                System.out.println("10. Lihat Destinasi & Jenis Wisata Terfavorit");
                System.out.println("11. Tampilkan Struktur Perjalanan (Secara PreOrder)");
                System.out.println("------------------------");
                System.out.println("--- NETWORK ANALYSIS ---");
                System.out.println("12. Tambah Rencana Perjalanan Antar Destinasi");
                System.out.println("13. Rencana Perjalanan Paling Efisien");
                System.out.println("14. Rute Tercepat ke Tempat Tujuan");
                System.out.println("15. Destinasi Sorting Demo");
                System.out.println("------------------------");
                System.out.println("16. Keluar");
                System.out.print("Pilih: ");

                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1:
                        try {
                            System.out.print("Nama Perjalanan: ");
                            String title = sc.nextLine();
                            System.out.print("Tanggal (yyyy-mm-dd): ");
                            LocalDate date = LocalDate.parse(sc.nextLine(), formatter);
                            System.out.print("Lokasi: ");
                            String location = sc.nextLine();
                            System.out.print("Kategori(alam/sejarah/kuliner/dll): ");
                            String category = sc.nextLine();

                            Event event = new Event(title, date, location, category);

                            allEvents.add(event);
                            undoStack.push(event);
                            redoStack.clear();
                            categories.add(category.toLowerCase());

                            locationMap.putIfAbsent(location, new ArrayList<>());
                            locationMap.get(location).add(event);

                            // Add location to network
                            network.addLocation(location);

                            bst.insert(event);
                            binaryTree.insert(event);

                            System.out.println("Perjalanan ditambahkan!");
                        } catch (DateTimeParseException e) {
                            System.out.println("Format tanggal salah!");
                        }
                        break;

                    case 2:
                        List<Event> sorted = new ArrayList<>();
                        bst.inOrderTraversal(sorted);
                        System.out.println("Semua Perjalanan (Terurut Tanggal):");
                        for (Event e : sorted) {
                            System.out.println("- " + e);
                        }
                        break;

                    case 3:
                        System.out.print("Masukkan tanggal (yyyy-mm-dd): ");
                        try {
                            LocalDate searchDate = LocalDate.parse(sc.nextLine(), formatter);
                            List<Event> foundEvents = bst.searchAllByDate(searchDate);
                            if (!foundEvents.isEmpty()) {
                                System.out.println("Perjalanan ditemukan:");
                                for (Event e : foundEvents) {
                                    System.out.println("- " + e);
                                }
                            } else {
                                System.out.println("Tidak ada perjalanan pada tanggal tersebut.");
                            }
                        } catch (DateTimeParseException e) {
                            System.out.println("Format tanggal salah!");
                        }
                        break;

                    case 4:
                        if (!undoStack.isEmpty()) {
                            Event undone = undoStack.pop();
                            allEvents.remove(undone);
                            redoStack.push(undone);
                            categories.remove(undone.category.toLowerCase());
                            if (locationMap.containsKey(undone.location)) {
                                locationMap.get(undone.location).remove(undone);
                            }
                            upcomingQueue.remove(undone);

                            bst = new EventBST();
                            binaryTree = new EventBinaryTree();
                            for (Event e : allEvents) {
                                bst.insert(e);
                                binaryTree.insert(e);
                            }

                            System.out.println("Perjalanan dibatalkan: " + undone.title);
                        } else {
                            System.out.println("Tidak ada yang bisa di-undo.");
                        }
                        break;

                    case 5:
                        if (!redoStack.isEmpty()) {
                            Event redone = redoStack.pop();
                            allEvents.add(redone);
                            undoStack.push(redone);
                            categories.add(redone.category.toLowerCase());
                            locationMap.putIfAbsent(redone.location, new ArrayList<>());
                            locationMap.get(redone.location).add(redone);

                            bst.insert(redone);
                            binaryTree.insert(redone);

                            System.out.println("Perjalanan dikembalikan: " + redone.title);
                        } else {
                            System.out.println("Tidak ada yang bisa di-redo.");
                        }
                        break;

                    case 6:
                        System.out.print("Judul Perjalanan untuk antrian: ");
                        String queueTitle = sc.nextLine();
                        Event foundQueue = allEvents.stream()
                                .filter(e -> e.title.equalsIgnoreCase(queueTitle))
                                .findFirst().orElse(null);
                        if (foundQueue != null) {
                            upcomingQueue.offer(foundQueue);
                            System.out.println("Ditambahkan ke antrian.");
                        } else {
                            System.out.println("Perjalanan tidak ditemukan.");
                        }
                        break;

                    case 7:
                        if (!upcomingQueue.isEmpty()) {
                            Event next = upcomingQueue.poll();
                            System.out.println("Ongoing: " + next);
                        } else {
                            System.out.println("Antrian kosong.");
                        }
                        break;

                    case 8:
                        System.out.println("Kategori Unik:");
                        for (String cat : categories) {
                            System.out.println("- " + cat);
                        }
                        break;

                    case 9:
                        System.out.print("Lokasi yang dicari: ");
                        String loc = sc.nextLine();
                        if (locationMap.containsKey(loc)) {
                            System.out.println("Perjalanan di " + loc + ":");
                            for (Event e : locationMap.get(loc)) {
                                System.out.println("- " + e);
                            }
                        } else {
                            System.out.println("Tidak ada perjalanan di lokasi tersebut.");
                        }
                        break;

                    case 10:
                        Map<String, Integer> lokasiCount = new HashMap<>();
                        Map<String, Integer> kategoriCount = new HashMap<>();

                        for (Event e : allEvents) {
                            lokasiCount.put(e.location, lokasiCount.getOrDefault(e.location, 0) + 1);
                            kategoriCount.put(e.category.toLowerCase(),
                                    kategoriCount.getOrDefault(e.category.toLowerCase(), 0) + 1);
                        }

                        String topLokasi = lokasiCount.entrySet().stream()
                                .max(Map.Entry.comparingByValue())
                                .map(Map.Entry::getKey).orElse("N/A");

                        String topKategori = kategoriCount.entrySet().stream()
                                .max(Map.Entry.comparingByValue())
                                .map(Map.Entry::getKey).orElse("N/A");

                        System.out.println(
                                "Lokasi Terbanyak: " + topLokasi + " (" + lokasiCount.getOrDefault(topLokasi, 0)
                                        + " event)");
                        System.out.println("Kategori Terpopuler: " + topKategori + " ("
                                + kategoriCount.getOrDefault(topKategori, 0) + " event)");
                        break;

                    case 11:
                        binaryTree.preOrderTraversal();
                        break;

                    case 12:
                        System.out.print("Lokasi asal: ");
                        String from = sc.nextLine();
                        System.out.print("Lokasi tujuan: ");
                        String to = sc.nextLine();
                        System.out.print("Biaya perjalanan (tanpa 3 digit belakang): ");
                        int cost = sc.nextInt();
                        sc.nextLine();

                        network.addConnection(from, to, cost);
                        System.out.println("Koneksi ditambahkan: " + from + " -> " + to + " (cost: " + cost + ")");
                        break;

                    case 13:
                        System.out.println("\n=== MINIMUM SPANNING TREE (Kruskal's Algorithm) ===");
                        List<Edge> mst = network.findMST();
                        if (!mst.isEmpty()) {
                            int totalCost = 0;
                            System.out.println("MST Edges:");
                            System.out
                                    .println(
                                            "MST menunjukkan cara termurah untuk menghubungkan semua lokasi perjalanan.");
                            for (Edge edge : mst) {
                                System.out.println("- " + edge);
                                totalCost += edge.weight;
                            }
                            System.out.println("Total MST Cost: " + totalCost);
                        } else {
                            System.out.println("Tidak ada koneksi yang tersedia untuk membuat MST.");
                        }
                        break;

                    case 14:
                        System.out.print("Lokasi awal untuk shortest path: ");
                        String startLocation = sc.nextLine();

                        if (network.getLocations().contains(startLocation)) {
                            System.out.println("\n=== SHORTEST PATH ANALYSIS (Dijkstra's Algorithm) ===");
                            System.out.println("Dari lokasi: " + startLocation);

                            Map<String, Integer> distances = network.shortestPath(startLocation);
                            System.out.println("Jarak terpendek ke semua lokasi:");

                            for (Map.Entry<String, Integer> entry : distances.entrySet()) {
                                String destination = entry.getKey();
                                int distance = entry.getValue();

                                if (distance == Integer.MAX_VALUE) {
                                    System.out.println("- " + destination + ": Tidak terjangkau");
                                } else {
                                    System.out.println("- " + destination + ": " + distance + " km");
                                }
                            }
                        } else {
                            System.out.println("Lokasi tidak ditemukan dalam network.");
                            System.out.println("Lokasi yang tersedia: " + network.getLocations());
                        }
                        break;

                    case 15:
                        System.out.println("\n=== DEMO SORTING ALGORITHMS ===");

                        // Create a copy of events for sorting demo
                        List<Event> eventsCopy = new ArrayList<>(allEvents);

                        if (eventsCopy.isEmpty()) {
                            System.out.println(
                                    "Tidak ada perjalanan untuk diurutkan. Tambahkan rencana perjalanan terlebih dahulu.");
                            break;
                        }

                        // Shuffle for demonstration
                        Collections.shuffle(eventsCopy);
                        System.out.println("Rencana Perjalanan sebelum sorting (random order):");
                        for (int i = 0; i < Math.min(5, eventsCopy.size()); i++) {
                            System.out.println("- " + eventsCopy.get(i));
                        }

                        // (using TimSort - hybrid of merge sort and insertion sort
                        Collections.sort(eventsCopy);

                        System.out.println("\nRencana Perjalanan setelah sorting by date:");
                        for (int i = 0; i < Math.min(5, eventsCopy.size()); i++) {
                            System.out.println("- " + eventsCopy.get(i));
                        }

                        // Sort by title using custom comparator
                        eventsCopy.sort(Comparator.comparing(e -> e.title));

                        System.out.println("\nRencana Perjalanan setelah sorting by title:");
                        for (int i = 0; i < Math.min(5, eventsCopy.size()); i++) {
                            System.out.println("- " + eventsCopy.get(i));
                        }
                        break;
                    case 16:
                        System.out.println(
                                "Terima kasih telah menggunakan program ini. Have fun in your adventure!");
                        return;

                    default:
                        System.out.println("Pilihan tidak valid.");
                }
            }
        } finally {
            sc.close();
        }
    }
}
