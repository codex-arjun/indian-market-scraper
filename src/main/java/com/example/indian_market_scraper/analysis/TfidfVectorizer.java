package com.example.indian_market_scraper.analysis;



import java.util.*;
import java.util.stream.Collectors;

public class TfidfVectorizer {
    private final Map<String, Integer> vocabulary = new HashMap<>();
    private final List<Map<String, Double>> documents = new ArrayList<>();

    public void fit(List<String> docs) {
        int index = 0;
        for (String doc : docs) {
            String[] words = doc.split("\\s+");
            Map<String, Double> termFreq = new HashMap<>();

            for (String w : words) {
                w = w.trim().toLowerCase();
                if (w.isEmpty()) continue;
                termFreq.put(w, termFreq.getOrDefault(w, 0.0) + 1.0);

                if (!vocabulary.containsKey(w)) {
                    vocabulary.put(w, index++);
                }
            }
            documents.add(termFreq);
        }
    }

    public double[][] transform() {
        int numDocs = documents.size();
        int vocabSize = vocabulary.size();
        double[][] tfidf = new double[numDocs][vocabSize];

        for (int d = 0; d < numDocs; d++) {
            Map<String, Double> termFreq = documents.get(d);
            for (Map.Entry<String, Double> entry : termFreq.entrySet()) {
                String term = entry.getKey();
                double tf = entry.getValue();
                double idf = computeIdf(term);
                tfidf[d][vocabulary.get(term)] = tf * idf;
            }
        }
        return tfidf;
    }

    private double computeIdf(String term) {
        double docCount = documents.size();
        double containingDocs = documents.stream().filter(d -> d.containsKey(term)).count();
        return Math.log(1 + docCount / (1 + containingDocs));
    }

    public List<String> getTopTerms(int n) {
        Map<String, Double> idfScores = vocabulary.keySet().stream()
                .collect(Collectors.toMap(t -> t, this::computeIdf));

        return idfScores.entrySet().stream()
                .sorted((a, b) -> Double.compare(b.getValue(), a.getValue()))
                .limit(n)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
