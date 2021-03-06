package com.playtika.abtests;

import java.math.BigInteger;
import java.security.MessageDigest;


/* Do not modify this class without contacting Oded Rotem */
public class TestAllocator
{
        private BigInteger numVariants, hundred;
        private int samplingPercentage;
        private String allocationToken, samplingToken;
        private MessageDigest md5;

        private void initialize(String AllocationToken, String SamplingToken, Integer NumVariants, Integer SamplingPercentage) throws Exception
        {
            md5 = MessageDigest.getInstance("MD5");
            this.allocationToken = AllocationToken;
            this.samplingToken = SamplingToken;
            this.numVariants = new BigInteger(NumVariants.toString());
            this.hundred = new BigInteger("100");
            this.samplingPercentage = SamplingPercentage;
        }

        public TestAllocator(String AllocationToken) throws Exception
        {
            this(AllocationToken, "", 2, 100); // defaults of 100% sampling with 2 variants. With no sampling, the sampling token is irrelevant
        }
        
        public TestAllocator(String AllocationToken, String SamplingToken, Integer NumVariants, Integer SamplingPercentage) throws Exception
        {
        	initialize(AllocationToken, SamplingToken,NumVariants, SamplingPercentage);
        }

        private String normalize(String s)
        {
            return s.toLowerCase().trim().replace("-","");
        }
        
        private String merge(String Input1, String Input2)
        {
        	return normalize(Input1) + normalize(Input2);
        }

        public Integer GetAllocation(String UserToken) throws Exception
        {
            if ((samplingPercentage==100)||(getBucket(UserToken, samplingToken, hundred) < samplingPercentage) ) // if user falls into sample
            {
            	return getBucket(UserToken, allocationToken, numVariants);   
            }
            else
            {
                return -1; // Not in test
            }
        }

        private Integer getHashMod(String input, BigInteger Modolus) throws Exception
    	{
    		byte[] bytesOfMessage;
			bytesOfMessage = input.getBytes("ASCII");
    		byte[] thedigest = md5.digest(bytesOfMessage);

            return (new BigInteger(1, thedigest)).mod(Modolus).intValue();
    	}

        private int getBucket(String UserToken, String BucketToken, BigInteger NumBuckets) throws Exception
        {
            String input = merge(UserToken,BucketToken);
            return getHashMod(input, NumBuckets);
        }
}
