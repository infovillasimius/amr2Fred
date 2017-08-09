/*
 * Copyright (C) 2016 anto
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
package amr2fred;

import java.io.File;
import org.apache.log4j.BasicConfigurator;

/**
 * @author anto
 */
public class Amr2fredWeb {

    /**
     * Get Predmatrix table singleton instance loaded in memory
     */
    PredMatrix pred = PredMatrix.getPredMatrix();
    RdfWriter rdfWriter = RdfWriter.getWriter();

    public String go(String amr, int writerMode, int check, boolean cb, boolean cb1, boolean proMode) {
        String fred = "", removed = "", err = "";
        if (amr.length() > 3) {

            Parser instance = Parser.getInstance();
            Node result = parse(amr, instance);

            /*
                    se il risultato dell'elaborazione del parser non è nullo avvia la visualizzazione dei risultati
                    a seconda dello stato delle flag cb e cb1, in particolare se cb è true manderà il risultato dell'elaborazione
                    del parser al controllo e rimozione degli errori.
                    Se è selezionata anche cb1 il writer inserirà gli elementi object (terzo elemento delle triple) come resource
                    e non come literal
                    Se cb è false per l'output verrà usato il metodo toString() implementato su Node, che prevede 
                    un output testuale non standard realizzato ad hoc per evidenziare la struttura dell'albero reso dall'eleborazione
             */
            if (result != null) {

                if (result.getTreStatus() > 0) {

                    if (check == 0) {
                        cb = false;
                        check = 1;
                    }
                } else {

                    cb = true;
                    check = 0;
                }

                if (cb && proMode) {
                    result = instance.check(result);
                    RdfWriter writer = RdfWriter.getWriter();
                    writer.setMode(Glossary.RdfWriteMode.values()[writerMode]);
                    writer.setObjectAsResource(cb1);
                    fred = (writer.writeRdf(result,cb1));
                } else {
                    fred = (result.toString());

                    if (result.getTreStatus() == 0) {
                        fred = DigraphWriter.toSvgString(result);
                    } else if (cb) {
                        result = instance.check(result);

                        fred = DigraphWriter.toSvgString(result);
                    } else {

                        fred = (result.toString());
                    }
                }

                /*
                        Quando sono stati rimossi dei nodi dall'albero, sono elencati nel riquadro in alto a destra
                 */
                if (!instance.getRemoved().isEmpty()) {
                    String removedNodes = "Removed nodes:\n";
                    for (Node n : instance.getRemoved()) {
                        removedNodes += n.toString2() + "\n";
                    }
                    removed = removedNodes;
                }

                if (instance.getRootCopy() != null) {
                    removed = ("AMR tree:\n" + instance.getRootCopy().toString2() + "\n\n" + removed);
                }

                /*
                        Verifica della presenza di errori e generazione di un messagio personalizzato in base al numero degli stessi
                 */
                if (result.getTreStatus() == 1) {
                    fred = ("Warning! Something went wrong: one node is not ok!\n\n") + fred;
                } else if (result.getTreStatus() > 999999) {
                    fred = ("Warning! Something went wrong: there is a recursive error!\n\n") + fred;
                } else if (result.getTreStatus() > 0) {
                    fred = ("Warning! Something went wrong: " + result.getTreStatus() + " nodes are not ok!\n\n") + fred;
                } else {
                    err = (" ");
                }
            } else {
                fred = ("Sintax error");
            }
        } else if (amr.length() == 0) {
            fred = ("No text");
        } else {
            fred = ("Sintax error");
        }
        return fred;
    }

    public File goPng(String amr) {
        if (amr.length() > 3) {
            Parser instance = Parser.getInstance();
            Node result = parse(amr, instance);
            if(result!=null){
                return DigraphWriter.toPng(instance.check(result));
            }
            
        }
        return null;
    }

    private Node parse(String amr, Parser instance) {

        Node result = null;

        if (amr.length() > 3) {

            while (amr.startsWith(" ")) {
                amr = amr.substring(1);
            }
            if (!amr.startsWith("(")) {
                amr = "(" + amr + ")";
            }
            result = instance.parse(amr);
        }

        return result;
    }

}
