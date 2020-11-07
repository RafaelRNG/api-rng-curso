package com.rng.apirng.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class URL {

    public static String decodeParam(String string){
        try{
            return URLDecoder.decode(string, "UTF-8");
        } catch(UnsupportedEncodingException e){
//            e.printStackTrace();
            return "";
        }
    }

    public static List<Long> decodeLongList(String string){
        return Arrays
                .asList(string.split(","))
                .stream()
                .map(elemento -> Long.parseLong(elemento))
                .collect(Collectors.toList());
    }
}
