package com.qingclass.dispatcher.global.util;

/**
 * An implementation of the KMP searching algorithm based on the understanding of this lecture: https://www.youtube.com/watch?v=GTJr8OvyEVQ
 * 
 * @author FLAG
 */

public class KMP {

	//直接用TEMPLATESENDJOBFINISH的lps结果,省去计算环节
	public static final int[] lpsTSJF = {0, 0, 0, 0, 0, 0, 1, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
	public static final char[] patternsTSJF = {'T','E','M','P','L','A','T','E','S','E','N','D','J','O','B','F','I','N','I','S','H'};

	public static int searchTSJF(String str) {
		char[] strs = str.toCharArray();
		int L=strs.length, N=patternsTSJF.length, i=L/2, j=0; // i: str pointer, j: pattern pointer
		if(L<N) return -1;
		while(i<L) {
			if(strs[i]==patternsTSJF[j]) { // same value found, move both str and pattern pointers to their right
			    ++i; 
			    ++j;
			    if(j==N) return i-N; // whole match found
			}
			else if(j>0) j = lpsTSJF[j-1]; // move pattern pointer to a previous safe location
			else ++i; // restart searching at next str pointer
		}
		return -1;
	}
}
