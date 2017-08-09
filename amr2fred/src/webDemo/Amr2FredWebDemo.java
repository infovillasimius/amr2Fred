/*
 * Copyright (C) 2017 anto
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

import amr2fred.Amr2fredWeb;
import com.sun.net.httpserver.HttpServer;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import org.apache.commons.io.FileUtils;
import static webDemo.Glossary.*;

/**
 * Run the Web Server
 * input par [IP address] [TCP Port]
 * if no par is provided default is localhost
 * @author anto
 */
public class Amr2FredWebDemo {

    private static Amr2fredWeb amr2fred = new Amr2fredWeb();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        String rawAddress = RAW_ADDRESS;
        String rawPort = RAW_PORT;

        if (args != null && args.length > 1 && !args[0].isEmpty() && !args[1].isEmpty()) {
            System.out.println(args[0]);
            System.out.println(args[1]);
            rawAddress = args[0];
            rawPort = args[1];
        }

        File dir = new File(PAGESDIR);
        if (!dir.isDirectory()) {
            dir.mkdir();
            dir.setWritable(true, false);
        } else {
            try {
                FileUtils.deleteDirectory(dir);
                dir.mkdir();
                dir.setWritable(true, false);
            } catch (IOException ex) {
                Logger.getLogger(StaticHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        int port = Integer.parseInt(rawPort);

        InetAddress address;
        try {
            byte[] addr = getAddress(rawAddress);
            address = InetAddress.getByAddress(addr);

            System.out.println("Il server opera all'indirizzo: " + address + " porta: " + port);

            InetSocketAddress addrs = new InetSocketAddress(address, port);
            try {

                HttpServer server = HttpServer.create();

                server.bind(addrs, MAX_REQUESTS);

                server.createContext(AMR2FRED, new Handler(amr2fred));

                server.createContext(WEB_ROOT, new StaticHandler());

                server.createContext(FRED, new FredHandler());

                server.start();
            } catch (IOException ex) {
                Logger.getLogger(Amr2FredWebDemo.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (UnknownHostException ex) {
            Logger.getLogger(Amr2FredWebDemo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    static private byte[] getAddress(String raw) {

        byte[] addr = {0, 0, 0, 0};

        if (raw.matches(IP_REGEX)) {
            for (int i = 0; i < 3; i++) {
                addr[i] = (byte) Integer.parseInt(raw.substring(0, raw.indexOf('.')));
                raw = raw.substring(raw.indexOf('.') + 1);
            }
            addr[3] = (byte) Integer.parseInt(raw);
            return addr;
        }

        return null;
    }

}
