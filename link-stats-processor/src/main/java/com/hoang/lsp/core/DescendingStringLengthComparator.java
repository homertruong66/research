package com.hoang.lsp.core;

import java.util.Comparator;

public class DescendingStringLengthComparator implements Comparator<String> {

    @Override
    public int compare(String str1, String str2) {
        if (str1.length() < str2.length()) {
            return 1;
        } else if (str1.length() == str2.length()) {
            return 0;
        } else {
            return -1;
        }
    }

}
