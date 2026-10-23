package ru.nsu.oop.hashmap;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HashTableTest {

    private HashTable<String, Integer> map;

    // put
    @BeforeEach
    void setUp() {
        map = new HashTable<>();
    }

    @Test
    void putCorrectData() {
        String key = "key";

        map.put(key, 15);
        assertEquals(1, map.getSize());
        assertEquals(15, map.get(key));
    }

    @Test
    void putNull() {
        String key = "key";

        map.put(key, null);
        assertNull(map.get(key));
    }

    @Test
    void putNullKey() {
        assertThrows(IllegalArgumentException.class, () -> map.put(null, 100));
    }

    @Test
    void intMinValueTest() {
        assertDoesNotThrow(() -> map.put("polygenelubricants", 42));
    }

    @Test
    void putHandlesKeyWithIntMinValueHashCode() {
        String key = "polygenelubricants";

        map.put(key, 42);

        assertEquals(42, map.get(key));
        assertTrue(map.containsKey(key));
    }

    @Test
    void twoStringsSameHashCode() {
        String a = "Aa";
        String b = "BB";

        map.put(a, 1);
        map.put(b, 2);
        assertEquals(1, map.get(a));
        assertEquals(2, map.get(b));
        assertEquals(2, map.getSize());
    }

    @Test
    void rehashTest() {
        for (int i = 0; i < 20; ++i) {
            map.put(String.valueOf(i), i);
        }

        assertEquals(20, map.getSize());
        for (int i = 0; i < 20; ++i) {
            assertEquals(i, map.get(String.valueOf(i)));
        }
    }


    //get
    @Test
    void getNullPointer() {
        assertThrows(IllegalArgumentException.class, () -> map.get(null));
    }

    @Test
    void getAbsentKey() {
        map.put("bbb", 123);
        assertNull(map.get("aaa"));
    }

    @Test
    void getExistingKey() {
        map.put("key", 42);
        assertEquals(42, map.get("key"));
    }

    // --- containsKey ---

    @Test
    void containsKeyExisting() {
        map.put("aaa", 1);
        assertTrue(map.containsKey("aaa"));
    }

    @Test
    void containsKeyAbsent() {
        map.put("aaa", 1);
        assertFalse(map.containsKey("bbb"));
    }

    @Test
    void containsKeyNull() {
        assertThrows(IllegalArgumentException.class, () -> map.containsKey(null));
    }

    // --- remove ---

    @Test
    void removeExistingKey() {
        map.put("aaa", 100);
        Integer removed = map.remove("aaa");

        assertEquals(100, removed);
        assertEquals(0, map.getSize());
        assertNull(map.get("aaa"));
    }

    @Test
    void removeAbsentKey() {
        Integer removed = map.remove("aaa");
        assertNull(removed);
        assertEquals(0, map.getSize());
    }

    @Test
    void removeNullKey() {
        assertThrows(IllegalArgumentException.class, () -> map.remove(null));
    }

    @Test
    void removeThenPut() {
        map.put("aaa", 1);
        map.remove("aaa");
        map.put("aaa", 2);

        assertEquals(1, map.getSize());
        assertEquals(2, map.get("aaa"));
    }

    // --- put: update existing key ---

    @Test
    void putUpdateExistingKey() {
        map.put("aaa", 1);
        map.put("aaa", 2);

        assertEquals(1, map.getSize());
        assertEquals(2, map.get("aaa"));
    }

    // --- toString ---

    @Test
    void toStringEmpty() {
        assertEquals("{}", map.toString());
    }

    @Test
    void toStringNonEmpty() {
        map.put("a", 1);
        map.put("b", 2);

        String str = map.toString();
        assertTrue(str.contains("a=1"));
        assertTrue(str.contains("b=2"));
        assertTrue(str.startsWith("{"));
        assertTrue(str.endsWith("}"));
    }

    // --- equals & hashCode ---

    @Test
    void equalsSelf() {
        assertEquals(map, map);
    }

    @Test
    void equalsNull() {
        assertNotEquals(null, map);
    }

    @Test
    void equalsDifferentType() {
        assertNotEquals("not a map", map);
    }

    @Test
    void equalsEmptyTables() {
        HashTable<String, Integer> other = new HashTable<>();
        assertEquals(map, other);
    }

    @Test
    void equalsSameContent() {
        map.put("a", 1);
        map.put("b", 2);

        HashTable<String, Integer> other = new HashTable<>();
        other.put("b", 2);
        other.put("a", 1);

        assertEquals(map, other);
    }

    @Test
    void equalsDifferentSize() {
        map.put("a", 1);

        HashTable<String, Integer> other = new HashTable<>();
        other.put("a", 1);
        other.put("b", 2);

        assertNotEquals(map, other);
    }

    @Test
    void equalsDifferentValues() {
        map.put("a", 1);

        HashTable<String, Integer> other = new HashTable<>();
        other.put("a", 2);

        assertNotEquals(map, other);
    }

    @Test
    void hashCodeContract() {
        map.put("a", 1);
        map.put("b", 2);

        HashTable<String, Integer> other = new HashTable<>();
        other.put("b", 2);
        other.put("a", 1);

        assertEquals(map.hashCode(), other.hashCode());
    }

    @Test
    void equalsWithNullValue() {
        map.put("a", null);

        HashTable<String, Integer> other = new HashTable<>();
        other.put("a", null);

        assertEquals(map, other);
    }

    @Test
    void equalsWithNullValueMismatch() {
        map.put("a", null);

        HashTable<String, Integer> other = new HashTable<>();
        other.put("a", 1);

        assertNotEquals(map, other);
    }

    // --- iterator ---

    @Test
    void iterateEmpty() {
        Iterator<HashTable.Entry<String, Integer>> it = map.iterator();
        assertFalse(it.hasNext());
        assertThrows(NoSuchElementException.class, it::next);
    }

    @Test
    void iterateAllEntries() {
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);

        int count = 0;
        for (HashTable.Entry<String, Integer> entry : map) {
            assertNotNull(entry.getKey());
            assertNotNull(entry.getValue());
            count++;
        }
        assertEquals(3, count);
    }

    @Test
    void iterateEntryAccessors() {
        map.put("key", 42);

        for (HashTable.Entry<String, Integer> entry : map) {
            assertEquals("key", entry.getKey());
            assertEquals(42, entry.getValue());
        }
    }

    @Test
    void iterateFailFastOnPut() {
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);

        Iterator<HashTable.Entry<String, Integer>> it = map.iterator();
        assertTrue(it.hasNext());
        it.next();

        map.put("x", 999);
        assertThrows(ConcurrentModificationException.class, it::next);
    }

    @Test
    void iterateFailFastOnRemove() {
        map.put("a", 1);
        map.put("b", 2);

        Iterator<HashTable.Entry<String, Integer>> it = map.iterator();
        assertTrue(it.hasNext());
        it.next();

        map.remove("a");

        assertThrows(ConcurrentModificationException.class, it::next);
    }

    // --- getSize ---

    @Test
    void getSizeEmpty() {
        assertEquals(0, map.getSize());
    }

    @Test
    void getSizeAfterOperations() {
        map.put("a", 1);
        assertEquals(1, map.getSize());

        map.put("b", 2);
        assertEquals(2, map.getSize());

        Integer removed = map.remove("a");
        assertEquals(1, removed);
        assertEquals(1, map.getSize());

        map.put("a", 3);
        assertEquals(2, map.getSize());
        assertEquals(3, map.get("a"));
    }

    @Test
    void removeElementFromCollisionChain() {
        String a = "Aa";
        String b = "BB";
        map.put(a, 1);
        map.put(b, 2);

        Integer removed = map.remove(b);

        assertEquals(2, removed);
        assertEquals(1, map.getSize());
        assertNull(map.get(b));
        assertEquals(1, map.get(a));
    }

}
