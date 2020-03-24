package com.muniz.vb.orders;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import java.io.IOException;

public class TestUtils {

    public static  String readJson(final String resource) throws IOException {
        return Resources.toString(Resources.getResource("jsons/" + resource), Charsets.UTF_8);
    }

}
