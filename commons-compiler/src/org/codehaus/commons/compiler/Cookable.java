
/*
 * Janino - An embedded Java[TM] compiler
 *
 * Copyright (c) 2001-2010, Arno Unkrig
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *    1. Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *    2. Redistributions in binary form must reproduce the above
 *       copyright notice, this list of conditions and the following
 *       disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *    3. The name of the author may not be used to endorse or promote
 *       products derived from this software without specific prior
 *       written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE
 * GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER
 * IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN
 * IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.codehaus.commons.compiler;

import java.io.*;

public abstract class Cookable implements ICookable {

    public abstract void cook(
        String optionalFileName,
        Reader r
    ) throws CompileException, ParseException, ScanException, IOException;

    // The "cook()" method family.

    public final void cook(
        Reader r
    ) throws CompileException, ParseException, ScanException, IOException {
        this.cook(null, r);
    }

    public final void cook(
        InputStream is
    ) throws CompileException, ParseException, ScanException, IOException {
        this.cook(null, is);
    }

    public final void cook(
        String      optionalFileName,
        InputStream is
    ) throws CompileException, ParseException, ScanException, IOException {
        this.cook(optionalFileName, is, null);
    }

    public final void cook(InputStream is, String optionalEncoding)
    throws CompileException, ParseException, ScanException, IOException {
        this.cook(optionalEncoding == null ? new InputStreamReader(is) : new InputStreamReader(is, optionalEncoding));
    }

    public final void cook(String optionalFileName, InputStream is, String optionalEncoding)
    throws CompileException, ParseException, ScanException, IOException {
        this.cook(
            optionalFileName,
            optionalEncoding == null ? new InputStreamReader(is) : new InputStreamReader(is, optionalEncoding)
        );
    }

    public final void cook(String s)
    throws CompileException, ParseException, ScanException {
        try {
            this.cook(new StringReader(s));
        } catch (IOException ex) {
            throw new RuntimeException("SNO: IOException despite StringReader");
        }
    }

    public final void cookFile(File file)
    throws CompileException, ParseException, ScanException, IOException {
        this.cookFile(file, null);
    }

    public final void cookFile(File file, String optionalEncoding)
    throws CompileException, ParseException, ScanException, IOException {
        InputStream is = new FileInputStream(file);
        try {
            this.cook(
                file.getAbsolutePath(),
                optionalEncoding == null ? new InputStreamReader(is) : new InputStreamReader(is, optionalEncoding)
            );
            is.close();
            is = null;
        } finally {
            if (is != null) try { is.close(); } catch (IOException ex) {}
        }
    }

    public final void cookFile(String fileName)
    throws CompileException, ParseException, ScanException, IOException {
        this.cookFile(fileName, null);
    }

    public final void cookFile(String fileName, String optionalEncoding)
    throws CompileException, ParseException, ScanException, IOException {
        this.cookFile(new File(fileName), optionalEncoding);
    }
}
