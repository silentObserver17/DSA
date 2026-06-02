package Graphs;

import java.util.*;

public class Traversal {
    record Word(String word, int level) {}

    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Queue<Word> queue = new LinkedList<>();

        Set<String> visited = new HashSet<>(wordList);

        queue.offer(new Word(beginWord, 0));
        visited.remove(beginWord);

        while(!queue.isEmpty()) {
            Word curr = queue.poll();
            String word = curr.word;
            int level = curr.level;

            if (word.equals(endWord)) return level;

            for(int i = 0; i < word.length(); i++) {
                char[] charArray = word.toCharArray();

                for(char ch = 'a'; ch <= 'z'; ch++) {
                    char original = charArray[i];
                    charArray[i] = ch;
                    String newWord = new String(charArray);

                    if(visited.contains(newWord)) {
                       queue.offer(new Word(newWord, level + 1));
                       visited.remove(newWord);
                    }

                    charArray[i] = original;
                }
            }
        }

        return 0;
    }

    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        Queue<List<String>> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>(wordList);

        List<List<String>> res = new ArrayList<>();
        List<String> usedOnLevel =  new ArrayList<>();

        queue.offer(new ArrayList<>(List.of(beginWord)));
        usedOnLevel.add(beginWord);

        int level = 0;

        while(!queue.isEmpty()) {
            List<String> current = queue.poll();

            if(current.size() > level) {
                level++;
                for(String word : usedOnLevel) {
                    visited.remove(word);
                }
                usedOnLevel.clear();
            }

            String word = current.get(current.size() - 1);
            if(word.equals(endWord)) {
                if(res.size() == 0) {
                    res.add(new ArrayList<>(current));
                } else if(res.getFirst().size() == current.size()) {
                    res.add(new ArrayList<>(current));
                }
            }

            for(int i = 0; i < word.length(); i++) {
                char[] charArray = word.toCharArray();
                for(char ch = 'a'; ch <= 'z'; ch++) {
                    if(ch == charArray[i]) continue;

                    charArray[i] = ch;
                    String newWord = new String(charArray);

                    if(visited.contains(newWord)) {
                        current.add(newWord);
                        queue.offer(new ArrayList<>(current));
                        usedOnLevel.add(newWord);
                        current.remove(newWord);
                    }
                }
            }
        }

        return res;
    }
}
