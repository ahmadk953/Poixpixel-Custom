package com.poixpixelcustom.util

import java.util.*

/**
 * Dynamic trie structure that can add/remove keys and recursively get matching strings for a key
 *
 * @author stzups
 */
class Trie {
    /**
     * TrieNode implementation that handles any character and keeps track of its own children and character
     */
    class TrieNode internal constructor(var character: Char) {
        var children: MutableList<TrieNode> = ArrayList()
        var endOfWord = false
    }

    private val root: TrieNode

    /**
     * Constructor that creates a new trie with a null root
     */
    init {
        root = TrieNode(Character.MIN_VALUE)
    }

    /**
     * Adds and links new TrieNodes to the trie for each character in the string
     *
     * @param key key to add to trie, can be longer than one character
     */
    fun addKey(key: String) {
        // Current trieNode to crawl through
        var trieNode: TrieNode? = root

        // Loop through each character of key
        for (i in 0 until key.length) {
            val index = key[i]
            val lastNode = trieNode
            trieNode = null
            for (node in lastNode!!.children) {
                if (node.character == index) {
                    trieNode = node
                    break
                }
            }
            if (trieNode == null) {
                trieNode = TrieNode(index)
                lastNode.children.add(trieNode) // Put this node as one of lastNode's children
                if (i == key.length - 1) { // Check if this is the last character of the key, indicating a word ending
                    trieNode.endOfWord = true
                }
            }
        }
    }

    /**
     * Removes TrieNodes for a key
     *
     * @param key key to remove
     */
    fun removeKey(key: String?) {

        // Fast-fail if empty / null
        if (key == null || key.isEmpty()) return
        val found = Collections.asLifoQueue(LinkedList<TrieNode>())

        // Build a stack of nodes matching the key
        var lastNode = root
        for (i in 0 until key.length) {
            val currChar = key[i]
            // Search for the node matching the character
            var charNode: TrieNode? = null
            for (loopNode in lastNode.children) {
                if (loopNode.character == currChar) {
                    charNode = loopNode
                    // There should only be one so we can fast-exit.
                    break
                }
            }
            lastNode = if (charNode != null) {
                found.add(charNode)
                charNode
            } else break
        }

        // Something clearly went wrong if this is the case.
        if (found.isEmpty() || found.peek().character != key[key.length - 1]) return

        // Removal Part

        // Get the node matching the last character of the key.
        var lastCharNode = found.poll()
        // Set end of word to false
        lastCharNode.endOfWord = false
        // Only remove the previous nodes if there are no children
        if (lastCharNode.children.isEmpty()) {
            var lastChar = lastCharNode.character
            while (!found.isEmpty()) {
                lastCharNode = found.poll()
                val nodeIterator = lastCharNode.children.iterator()
                while (nodeIterator.hasNext()) {
                    if (nodeIterator.next().character == lastChar) {
                        nodeIterator.remove()
                        break
                    }
                }
                if (lastCharNode.endOfWord || !lastCharNode.children.isEmpty()) break
                lastChar = lastCharNode.character
            }
        }
    }

    /**
     * Gets all matching strings and their children for a key
     *
     * @param key string to search for in tree
     * @return matching strings and their children
     */
    fun getStringsFromKey(key: String): List<String> {
        // Empty key means find all nodes, starting from the root node
        if (key.length == 0) {
            return getChildrenStrings(root, ArrayList())
        }
        val strings: MutableList<String> = ArrayList()
        var nodes: MutableMap<TrieNode, String> = HashMap() // Contains a key for each TrieNode
        nodes[root] = "" // Start with the root node
        for (i in 0 until key.length) {
            val newNodes: MutableMap<TrieNode, String> = HashMap() // An updated version of nodes, will not contain the old values
            val index = key[i].lowercaseChar()
            for ((key1, value) in nodes) { // Loop through the old nodes
                for (node in key1.children) {
                    if (node.character.lowercaseChar() == index) {
                        val realKey = value + node.character
                        newNodes[node] = realKey // entry.getValue is the old key for the node, for example "bana" as entry.getValue() and "n" as listNode.character resulting in "banan" for listNode
                        if (i == key.length - 1) { // Check if this is the last character of the key, indicating a word ending. From here we need to find all the possible children
                            for (string in getChildrenStrings(node, ArrayList())) { // Recursively find all children
                                strings.add(realKey + string) // Add the key to the front of each child string
                            }
                        }
                    }
                }
            }
            nodes = newNodes
        }
        return strings
    }

    companion object {
        private const val MAX_RETURNS = 100

        /**
         * Recursively find all children of a TrieNode, and add to a list of strings
         *
         * @param find the current TrieNode to search through its own children
         * @param found strings that have already been found
         * @return strings of all children found, with this TrieNode's character in front of each string
         */
        private fun getChildrenStrings(find: TrieNode, found: MutableList<String>): List<String> {
            val childrenStrings: MutableList<String> = ArrayList() // Create re-usable list to prevent object allocation
            for (trieNode in find.children) { // Loop through each child
                if (found.size + 1 > MAX_RETURNS) {
                    return found
                }
                if (trieNode.endOfWord) // End of the word, so explicitly add this character.
                    found.add(trieNode.character.toString())

                // Only get children if the node has children.
                if (!trieNode.children.isEmpty()) {
                    childrenStrings.clear()
                    for (string in getChildrenStrings(trieNode, childrenStrings)) {
                        if (found.size + 1 > MAX_RETURNS) {
                            return found
                        } else {
                            found.add(trieNode.character.toString() + string)
                        }
                    }
                }
            }
            return found
        }
    }
}