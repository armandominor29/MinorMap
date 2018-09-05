import java.util.LinkedList;
import java.util.Queue;

/*** Created by armandominor on 10/2/16.*/
/*** Two Probe Chain Map */
public class TwoProbeChainMap <Key, Value> implements Map<Key, Value>
{
    /*** input class */
    private class Entry
    {
        public Key key;
        public Value value;
        public Entry (Key k, Value v) {
            key = k;
            value = v;
        }
    }

    private int N; // number of key-value pairs
    private int M; // hash table size

    private LinkedList<Entry>[] entries;

    /*** Constructor. */
    public TwoProbeChainMap()
    {
        this(997);
    }
    /*** Create new map */
    public TwoProbeChainMap(int M)
    {
        this.N = 0;
        this.M = M;
        entries = new LinkedList[M];
        for (int i = 0; i < M; i++)
            entries[i] = new LinkedList<Entry>();
    }
    /*** hash functions. */
    private int hash(Key key)
    {
        return (key.hashCode() & 0x7fffffff) % M;
    }

    private int hash2(Key key)
    {
        return (((key.hashCode() & 0x7fffffff) % M) * 31) % M;
    }
    /*** get key value  */
    public Value get(Key key)
    {
        for(Entry entry : entries[hash(key)])
            if(key.hashCode() == entry.key.hashCode())
                return entry.value;

        for(Entry entry : entries[hash2(key)])
            if(key.hashCode() == entry.key.hashCode())
                return entry.value;

        return null;

    }

    /*** insert key-value pair*/
    public void put(Key key, Value val)
    {
        boolean added = false;

        for(Entry entry : entries[hash(key)])
            if(key.hashCode() == entry.key.hashCode())
            {
                entry.value = val;
                added = true;
            }

        if(!added) {
            entries[hash(key)].add(new Entry(key, val));
            N++;
        }
        if(added) {
            entries[hash2(key)].add(new Entry(key, val));
            N++;
        }

    }

    /*** Returns the number of key-value pairs*/
    public int size()
    {
        return N;
    }

    /** true if 0*/
    public boolean isEmpty()
    {
        return N == 0;
    }

    /** True if k is found*/
    public boolean contains(Key k)
    {
        return get(k) != null;
    }

    /** Removes key*/
    public void remove(Key key)
    {
        if(contains(key)) {
            TwoProbeChainMap.Entry target = null;
            for(TwoProbeChainMap.Entry e : entries[hash(key)])
                if(e.key == key)
                    target = e;

            entries[hash(key)].remove(target);
            N--;
        }

    }
    /*** Returns all k. */
        public Iterable<Key> keys()
        {
        Queue<Key> queue = new LinkedList<Key>();

        for (int i = 0; i < M; i++)
            for(Entry e : entries[i])
                queue.add(e.key);

        return queue;
    }
}