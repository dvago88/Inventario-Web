package com.danielvargas.InventarioWeb.searching;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import java.util.List;
import java.util.Set;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * An trie data structure that implements the Dictionary and the AutoComplete ADT
 *
 * @author Daniel
 */
public class AutoCompletar implements Dictionary, AutoComplete {

    private TrieNode root;
    private int size;


    public AutoCompletar() {
        root = new TrieNode();
        size = 0;
    }


    /**
     * Insert a word into the trie.
     * For the basic part of the assignment (part 2), you should convert the
     * string to all lower case before you insert it.
     * <p>
     * This method adds a word by creating and linking the necessary trie nodes
     * into the trie, as described outlined in the videos for this week. It
     * should appropriately use existing nodes in the trie, only creating new
     * nodes when necessary. E.g. If the word "no" is already in the trie,
     * then adding the word "now" would add only one additional node
     * (for the 'w').
     *
     * @return true if the word was successfully added or false if it already exists
     * in the dictionary.
     */
    public boolean addWord(String word) {
        if (word.length() == 0) {
            return false;
        }
        word = word.toLowerCase();
        char firstChild = word.charAt(0);
        TrieNode holder = root.insert(firstChild);
        TrieNode next;
        boolean checker = true;
        if (holder == null) {
            holder = root.getChild(firstChild);
            checker = false;
        }

        for (int i = 1; i < word.length(); i++) {
            next = holder.insert(word.charAt(i));
            if (next == null) {
                next = holder.getChild(word.charAt(i));
                checker = false;
            } else {
                checker = true;
            }
            holder = next;
        }
        if (!holder.endsWord()) {
            size++;
        }
        holder.setEndsWord(true);
        return checker;
    }

    /**
     * Return the number of words in the dictionary.  This is NOT necessarily the same
     * as the number of TrieNodes in the trie.
     */
    public int size() {
        return size;
    }


    /**
     * Returns whether the string is a word in the trie, using the algorithm
     * described in the videos for this week.
     */
    @Override
    public boolean isWord(String s) {
        if (s.length() == 0) {
            return false;
        }
        s = s.toLowerCase();
        char firstChild = s.charAt(0);
        TrieNode holder = root.getChild(firstChild);
        if (holder == null) {
            return false;
        }

        for (int i = 1; i < s.length(); i++) {
            holder = holder.getChild(s.charAt(i));
            if (holder == null) {
                return false;
            }
        }
        return holder.endsWord();
    }

    /**
     * Return a list, in order of increasing (non-decreasing) word length,
     * containing the numCompletions shortest legal completions
     * of the prefix string. All legal completions must be valid words in the
     * dictionary. If the prefix itself is a valid word, it is included
     * in the list of returned words.
     * <p>
     * The list of completions must contain
     * all of the shortest completions, but when there are ties, it may break
     * them in any order. For example, if there the prefix string is "ste" and
     * only the words "step", "stem", "stew", "steer" and "steep" are in the
     * dictionary, when the user asks for 4 completions, the list must include
     * "step", "stem" and "stew", but may include either the word
     * "steer" or "steep".
     * <p>
     * If this string prefix is not in the trie, it returns an empty list.
     *
     * @param prefix         The text to use at the word stem
     * @param numCompletions The maximum number of predictions desired.
     * @return A list containing the up to numCompletions best predictions
     */
    @Override
    public List<String> predictCompletions(String prefix, int numCompletions) {
        prefix = prefix.toLowerCase();
        LinkedList<String> predictions = new LinkedList<>();
        int contador = 0;

        if (numCompletions == 0) {
            return predictions;
        }
        TrieNode holder;

        try {
            char firstChild = prefix.charAt(0);
            holder = root.getChild(firstChild);
        } catch (IndexOutOfBoundsException iobe) {
            holder = root;
        }
        if (holder == null) {
            return predictions;
        }

        for (int i = 1; i < prefix.length(); i++) {
            holder = holder.getChild(prefix.charAt(i));
            if (holder == null) {
                return predictions;
            }
        }

        LinkedList<TrieNode> trieNodes = new LinkedList<>();
        trieNodes.add(holder);

        while (!trieNodes.isEmpty() && contador < numCompletions) {
            holder = trieNodes.pollFirst();
            Set<Character> llaves = holder.getValidNextCharacters();
            for (char c : llaves) {
                trieNodes.add(holder.getChild(c));
            }
            if (holder.endsWord()) {
                predictions.add(holder.getText());
                contador++;
            }
        }

        return predictions;
    }

    // For debugging
    public void printTree() {
        printNode(root);
    }

    /**
     * Do a pre-order traversal from this node down
     */
    public void printNode(TrieNode curr) {
        if (curr == null)
            return;

        System.out.println(curr.getText());

        TrieNode next = null;
        for (Character c : curr.getValidNextCharacters()) {
            next = curr.getChild(c);
            printNode(next);
        }
    }
}