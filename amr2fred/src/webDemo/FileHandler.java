/*
 * Copyright (C) 2017 Antonello
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package webDemo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;
import static webDemo.Glossary.*;
import webDemo.resources.Loader;

/**
 * Extract requested file from the jar
 * @author Antonello
 */
public class FileHandler {

     /**
     * Extract requested file from the jar
     * @param nomefile 
     */
    void getFile(String nomefile) {

        File dir = new File(PAGESDIR);
        if (!dir.isDirectory()) {
            dir.mkdir();
            dir.setWritable(true, false);
        }

        File file = new File(PAGESDIR + nomefile);

        if (!file.exists()) {
            try {
                //Usa la classe fake Loader per trovare i file voluti contenuti nella stessa cartella
                InputStream link = (Loader.class.getResourceAsStream(nomefile));
                Files.copy(link, file.getAbsoluteFile().toPath());
                file.setWritable(true, false);
            } catch (IOException ex) {
                Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}




