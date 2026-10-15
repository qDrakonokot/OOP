package ru.nsu.oop.hashmap;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;

/**
 * Хеш-таблица на основе массива хеш-кодов и цепочек (chaining).
 *
 * <p>Реализует хранилище пар "ключ-значение" со средним временем операций
 * вставки, поиска и удаления O(1). При заполнении более чем на 75% автоматически увеличивает
 * ёмкость вдвое (rehashing).</p>
 *
 * <p>Поддерживает fail-fast итерацию: при обнаружении структурных изменений
 * во время обхода выбрасывается {@link ConcurrentModificationException}.</p>
 *
 * <p>Ключи не могут быть {@code null} — попытка вставить или найти элемент
 * с нулевым ключом приводит к {@link IllegalArgumentException}.</p>
 *
 * @param <K> тип ключей
 * @param <V> тип значений
 */
public class HashTable<K, V> implements Iterable<HashTable.Entry<K, V>> {

    private static final int INITIAL_CAPACITY = 16;
    private static final float LOAD_FACTOR = 0.75f;

    private List<Entry<K, V>> table;
    private int size;
    private int modCount;

    /**
     * Создаёт новую хеш-таблицу с начальной ёмкостью 16.
     */
    public HashTable() {
        this.size = 0;
        this.modCount = 0;
        this.table = new ArrayList<>(INITIAL_CAPACITY);

        // без заполнения нулями мы не сможем обращаться к неинициализированным индексам
        for (int i = 0; i < INITIAL_CAPACITY; i++) {
            table.add(null);
        }
    }

    /**
     * Вставляет пару "ключ-значение" в таблицу.
     *
     * <p>Если ключ уже существует, значение обновляется. Ёмкость таблицы
     * автоматически увеличивается при превышении коэффициента загрузки 0.75.</p>
     *
     * @param key   ключ, не может быть {@code null}
     * @param value значение
     * @throws IllegalArgumentException если {@code key} равен {@code null}
     */
    public void put(K key, V value) {
        if (key == null) {
            // мы обсуждали, что их использовать тупо, поэтому просто запрещу их
            throw new IllegalArgumentException("Null pointer forbidden");
        }

        int hash = key.hashCode() & 0x7FFFFFFF; // чтоб случайно не нарваться на инт_мин_валуе
        int index = hash % table.size();
        Entry<K, V> current = table.get(index);
        while (current != null) {
            if (current.key.equals(key)) {
                current.value = value;
                return;
            }
            current = current.next;
        }

        Entry<K, V> newEntry = new Entry<>(key, value, table.get(index));
        table.set(index, newEntry);
        size++;
        modCount++;

        if (size > table.size() * LOAD_FACTOR) {
            resize();
        }
    }

    /**
     * Возвращает значение, связанное с заданным ключом.
     *
     * @param key ключ для поиска, не может быть {@code null}
     * @return значение, связанное с ключом, или {@code null}, если ключ не найден
     * @throws IllegalArgumentException если {@code key} равен {@code null}
     */
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Null pointer forbidden");
        }

        int hash = key.hashCode() & 0x7FFFFFFF;
        int index = hash % table.size();
        Entry<K, V> current = table.get(index);
        while (current != null) {
            if (current.key.equals(key)) {
                return current.value;
            }
            current = current.next;
        }

        return null;
    }

    /**
     * Удаляет элемент с заданным ключом из таблицы.
     *
     * @param key ключ удаляемого элемента, не может быть {@code null}
     * @return значение, связанное с ключом, или {@code null}, если ключ не найден
     * @throws IllegalArgumentException если {@code key} равен {@code null}
     */
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Null pointer forbidden");
        }

        int hash = key.hashCode() & 0x7FFFFFFF;
        int index = hash % table.size();
        Entry<K, V> previous = null;
        Entry<K, V> current = table.get(index);
        while (current != null) {
            if (current.key.equals(key)) {
                if (previous == null) {
                    table.set(index, current.next);
                } else {
                    previous.next = current.next;
                }

                size--;
                modCount++;
                return current.value;
            }
            previous = current;
            current = current.next;
        }

        return null;
    }

    /**
     * Проверяет, содержит ли таблица заданный ключ.
     *
     * @param key ключ для проверки, не может быть {@code null}
     * @return {@code true}, если ключ найден
     * @throws IllegalArgumentException если {@code key} равен {@code null}
     */
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Null pointer forbidden");
        }

        int hash = key.hashCode() & 0x7FFFFFFF;
        int index = hash % table.size();
        Entry<K, V> current = table.get(index);

        while (current != null) {
            if (current.key.equals(key)) {
                return true;
            }
            current = current.next;
        }

        return false;
    }

    /**
     * Возвращает строковое представление таблицы.
     *
     * <p>Формат: {@code {key1=value1, key2=value2, ...}}.</p>
     *
     * @return строковое представление таблицы
     */
    @Override
    public String toString() {
        if (size == 0) {
            return "{}";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("{");

        boolean first = true;
        for (Entry<K, V> entry : this) {
            if (!first) {
                sb.append(", ");
            }
            sb.append(entry.key).append("=").append(entry.value);
            first = false;
        }

        sb.append("}");
        return sb.toString();
    }

    /**
     * Проверяет логическое равенство с другим объектом.
     *
     * <p>Две таблицы считаются равными, если они имеют одинаковый размер
     * и содержат одинаковые пары "ключ-значение" (порядок не учитывается).</p>
     *
     * @param obj объект для сравнения
     * @return {@code true}, если объекты логически равны
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof HashTable<?, ?>)) {
            return false;
        }

        @SuppressWarnings("unchecked")
        HashTable<K, V> other = (HashTable<K, V>) obj;

        if (this.size != other.size) {
            return false;
        }

        try {
            for (Entry<K, V> entry : this) {
                K key = entry.key;
                V value = entry.value;

                if (!other.containsKey(key)) {
                    return false;
                }

                Object otherValue = other.get(key);

                if (value == null) {
                    if (otherValue != null) {
                        return false;
                    }
                } else {
                    if (!value.equals(otherValue)) {
                        return false;
                    }
                }
            }
        } catch (ClassCastException | NullPointerException e) {
            return false;
        }

        return true;
    }

    /**
     * Возвращает хеш-код таблицы.
     *
     * <p>Хеш-код вычисляется как сумма хеш-кодов всех пар "ключ-значение",
     * согласно общему контракту {@link Object#hashCode()}.</p>
     *
     * @return хеш-код таблицы
     */
    @Override
    public int hashCode() {
        int hash = 0;
        for (Entry<K, V> entry : this) {
            int keyHash = (entry.key == null ? 0 : entry.key.hashCode());
            int valueHash = (entry.value == null ? 0 : entry.value.hashCode());
            hash += 31 * keyHash + valueHash;
        }
        return hash;
    }

    /**
     * Получение размера текущей хеш-таблицы.
     *
     * @return размер таблицы.
     */
    public int getSize() {
        return size;
    }

    private void resize() {
        int newCapacity = table.size() * 2;
        List<Entry<K, V>> newTable = new ArrayList<>(newCapacity);
        for (int i = 0; i < newCapacity; i++) {
            newTable.add(null);
        }

        for (Entry<K, V> current : table) {
            while (current != null) {
                int hash = current.key.hashCode() & 0x7FFFFFFF;
                int index = hash % newCapacity;

                Entry<K, V> nextEntry = current.next;
                current.next = newTable.get(index);
                newTable.set(index, current);
                current = nextEntry;
            }
        }

        table = newTable;
    }

    /**
     * Возвращает итератор по всем элементам таблицы.
     *
     * <p>Итератор является fail-fast: при обнаружении структурных изменений
     * во время обхода выбрасывается {@link ConcurrentModificationException}.</p>
     *
     * @return итератор по элементам таблицы
     */
    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new HashTableIterator();
    }

    /**
     * Представляет пару "ключ-значение" в хеш-таблице.
     *
     * <p>Ключ является неизменяемым (final). Класс является статическим
     * вложенным и инкапсулирует внутреннюю структуру цепочек.</p>
     *
     * @param <K> тип ключей
     * @param <V> тип значений
     */
    public static final class Entry<K, V> {

        private final K key;
        private V value;
        private Entry<K, V> next;

        private Entry(K key, V value, Entry<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        /**
         * Возвращает ключ этой записи.
         *
         * @return ключ
         */
        public K getKey() {
            return key;
        }

        /**
         * Возвращает значение этой записи.
         *
         * @return значение
         */
        public V getValue() {
            return value;
        }

    }

    private class HashTableIterator implements Iterator<Entry<K, V>> {

        private final int expectedModCount;
        private int currentBucketIndex;
        private Entry<K, V> nextEntry;

        public HashTableIterator() {
            this.expectedModCount = modCount;
            this.currentBucketIndex = 0;
            this.nextEntry = null;

            advanceToNextNode();
        }

        @Override
        public boolean hasNext() {
            return nextEntry != null;
        }

        @Override
        public Entry<K, V> next() {
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException("Table was modified during iteration");
            }

            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }

            Entry<K, V> result = nextEntry;

            if (result.next != null) {
                nextEntry = result.next;
            } else {
                advanceToNextNode();
            }

            return result;
        }

        private void advanceToNextNode() {
            while (currentBucketIndex < table.size()) {
                Entry<K, V> current = table.get(currentBucketIndex);
                currentBucketIndex++;

                if (current != null) {
                    nextEntry = current;
                    return;
                }
            }

            nextEntry = null;
        }
    }
}