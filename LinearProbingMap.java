import java.util.LinkedList;
import java.util.Queue;

/*** Created by Armando Minor on 10/1/16. */

public class LinearProbingMap<Key, Value> implements Map<Key, Value>
{

    private static final int capacity = 997; // capacity integer size
    private int nmbr;                        // number of key-value pairs
    private int max = capacity;              // capacity of linear probing map
    private Key[] k;
    private Value[] v;

    /*** Create 997 symbol table.*/
    public LinearProbingMap()
    {
        this(capacity);
    }

/*** A constructor that defaults to an array of size 997. */
    public LinearProbingMap(int max)
    {
        max = capacity;
        nmbr = 0;
        k = (Key[])   new Object[max];
        v = (Value[]) new Object[max];
    }

    /*** hash function. */
    private int hash(Key key)
    {
        return (key.hashCode() & 0x7fffffff) % max;
    }

    /*** Puts a key-value pair into the map. */
    public void put(Key key, Value val)
    {
        int i;

        for (i = hash(key); k[i] != null; i = (i + 1) % max)
        {
            if (k[i].equals(key))
            {
                v[i] = val;
                return;
            }
        }

        k[i] = key;
        v[i] = val;
        nmbr++;
    }

    /*** Get value, if not found return null*/
    public Value get(Key key){
        int i = hash(key);
        while (k[i] != null)
        {
            if (k[i].equals(key))
                return v[i];
        }
        return null;
    }

    /** Removes key and rehashes if necessary, ,may not compile*/
    public void remove(Key key)
    {
        if (!contains(key)) return;

        int i = hash(key);
        while (!key.equals(k[i]))
        {
            i = (i + 1) % max;
        }

        k[i] = null;
        v[i] = null;

        i = (i + 1) % max;
        while (k[i] != null)
        {
            Key   kReHash = k[i];
            Value vReHash = v[i];
            k[i] = null;
            v[i] = null;
            nmbr--;
            put(kReHash, vReHash);
            i = (i + 1) % max;
        }
    }

    /*** Checks if the map contains a particular key. */
    public boolean contains(Key key)
    {

        return get(key)!=null;
    }

    /*** Return true or false statement*/
    public boolean isEmpty()
    {
        return size()==0;
    }

    /*** Returns the number of key-value pairs*/
   public int size()
   {
        return nmbr;
   }

    /*** Returns all k. */
    public Iterable<Key> keys()
    {
        Queue<Key> keyQueue = new LinkedList<Key>();
        for (int i = 0; i < max; i++)
            if (k[i] != null) keyQueue.add(k[i]);
        return keyQueue;
    }
}
