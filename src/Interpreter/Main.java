package Interpreter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;
import java.util.concurrent.TimeUnit;


import java.io.FileNotFoundException;

public class Main {
    private static void logo()
    {
        System.out.println("                                                            \n" +
                "                                           ```              \n" +
                "                           `..         `:osys-              \n" +
                "                            :sys+:`   -+sso:                \n" +
                "                             `/syys:`/oso:``...`            \n" +
                "                          ``````:sso/+ss+:ossyyyso/-        \n" +
                "                      -/+osoooo//:+soooooooo++/::-.`        \n" +
                "                    `-......--:/++ooooooooosoo++:.          \n" +
                "                          `..:/+++++o++++++:-:/+oso.        \n" +
                "                    `.` `-///:-:+/+++++yo+++/.    .-        \n" +
                "         `/ss+-   :oo-  ./+:-``:/+++osys+-+++/.             \n" +
                "         ` `:oo+-oo::/oo+++/` .+ooo/.-`   `/ooo.            \n" +
                "     .:/oo+++:osoo++/:. `o+-  .oso:        `+ss:            \n" +
                "        .-/++++ooo++///-.-+   `os+`         `os:            \n" +
                "      .+o++//+/+++so.-/+o+-   `+o`           -s`            \n" +
                "     -so/+/-`/++//o.   `-os- `/+-             -             \n" +
                "     +- :+:  :s+` `/:     -+`-+/                            \n" +
                "        .+`  `/-   `:/      `/o.                            \n" +
                "         `          `:/     -+/                             \n" +
                "                     `//   `/+-                             \n" +
                "                      `+-  .+o.                             \n" +
                "                       -+. :++`                             \n" +
                "                        //`/+s-                             \n" +
                "                        -+-/ss.                             \n" +
                "                        -o+oso/:                            \n" +
                "                      .:/sosssso/`                          \n" +
                "                    `:/ossssssss+-`                         \n" +
                "            ````....-----------------.....```               ");

        System.out.println("oooo                  oooo          .oooooo.    .oooooo..o \n" +
                "`888                  `888         d8P'  `Y8b  d8P'    `Y8 \n" +
                " 888  oooo   .ooooo.   888  oooo  888      888 Y88bo.      \n" +
                " 888 .8P'   d88' `88b  888 .8P'   888      888  `\"Y8888o.  \n" +
                " 888888.    888   888  888888.    888      888      `\"Y88b \n" +
                " 888 `88b.  888   888  888 `88b.  `88b    d88' oo     .d8P \n" +
                "o888o o888o `Y8bod8P' o888o o888o  `Y8bood8P'  8\"\"88888P'  \n" +
                "____________________________________________________________\n");
    }
    public static void main(String[] arg) throws FileNotFoundException {
        logo();
        Shell shell = new Shell();
        shell.start();
    }
}
