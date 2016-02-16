package com.playtika.abtests;


import java.util.HashMap;

public class Main {

    public static void main(String[] args)
    {
        String s = java.util.UUID.randomUUID().toString();
        TestAllocator alloc = new TestAllocator(s,"", 2,50);
        HashMap<Integer, Integer> results = new HashMap<Integer,Integer>();
        Integer res,curr;
        for (Integer i = 0; i<100000; i+=1)
        {
            res = alloc.GetAllocation(java.util.UUID.randomUUID().toString());
            curr = results.containsKey(res)?results.get(res)  : 0;
            results.put(res, curr+1);
        }
        for (Integer r:results.keySet())
        {
            System.out.println(r.toString() + " : " + results.get(r).toString());
        }
    }


}
