# Assignment 4 — Smart City / Smart Campus Scheduling

# Almukhamedov Temirlan

## Goal
Integrate three major graph algorithms for task scheduling and optimization in smart-city service networks:

1. Strongly Connected Components (SCC)
2. Topological Ordering of Condensation Graph
3. Shortest and Longest Paths in DAG

---

## Implementation Structure

DAA4/
├── data/                      → datasets and generated graphs  
├── src/  
│   └── main/java/graph/  
│       ├── model/             → Graph, Node, Edge  
│       ├── io/                → JsonParser  
│       ├── scc/               → TarjanSCC, SCCResult  
│       ├── topo/              → CondensationGraph, KahnTopoSorter  
│       ├── dagsp/             → DAGShortestPaths, DAGLongestPath  
│       ├── metrics/           → Metrics, SimpleMetrics  
│       └── util/              → DatasetGenerator, GraphToJson, Main  
└── test/java/graph/           → JUnit tests for all modules

---

## Build & Run

**Compile project:**  
mvn clean compile

**Run main program with dataset:**  
mvn --% exec:java -Dexec.args=data/tasks.json

**Generate datasets (small/medium/large):**  
mvn --% exec:java -Dexec.args=generate

Each run prints:
- Parsed graph
- SCC components and their sizes
- Topological order of condensation DAG
- Shortest and longest paths (critical path)

---

## Dataset Summary

| Category | Nodes (n) | Description | Count |
|-----------|------------|--------------|--------|
| Small     | 6–10       | Simple cases (1–2 cycles or pure DAG) | 3 |
| Medium    | 10–20      | Mixed structures with several SCCs    | 3 |
| Large     | 20–50      | Dense graphs for performance tests    | 3 |

All datasets use edge weights (`"weight_model": "edge"`).

---

## Metrics

| Task | Metric Fields | Meaning |
|------|----------------|---------|
| SCC (Tarjan) | DFS visits, edges | Search operations |
| Topo (Kahn) | Push / pop count | Queue activity |
| DAG SP/LP | Relaxations | Dynamic-program steps |
| All | Time (ns) | Measured via System.nanoTime() |

---

## Results and Analysis

- **SCC vs Graph Structure:** Dense graphs increase DFS calls; cyclic graphs produce larger components.
- **Topological Ordering:** Linear in edges, efficient for all dataset sizes.
- **Shortest Paths:** O(V+E) in DAG, stable and fast.
- **Longest Path (Critical Path):** Provides total task duration after SCC compression.

---

## Practical Recommendations

- Apply SCC compression before any task dependency analysis.
- Use Kahn’s algorithm for efficient DAG topological order.
- Use longest path to identify critical chains of dependent tasks.

---

## Testing and Reproducibility

Run all unit tests:  
mvn test

JUnit cases cover SCC, Topological Sort, and DAG Shortest/Longest Path algorithms.

---

## Environment

- Java 17
- Maven 3.9+
- Jackson 2.15
- JUnit 5.10

Implemented by **Temirlan Almukhamedov**

---

## Repository Checklist

- ✅ SCC + Condensation + Topological Sort
- ✅ DAG Shortest and Longest Path
- ✅ Report and Analysis
- ✅ Metrics and Data Generator
- ✅ Clean Build and JUnit Tests
