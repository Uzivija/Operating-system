package Interpreter;

import java.util.ArrayList;

public class Perm_List extends User_List{
    public ArrayList<Perm> PermList = new ArrayList<>();

    Perm_List(String userName)
    {
        int sizeU = Shell.getUserList().UserList.size();
        //System.out.println(sizeU);

        for(int i=0; i<sizeU; i++) {
            if(Shell.getUserList().UserList.get(i).getUserName().equals(userName) || Shell.getUserList().UserList.get(i).getUserName().equals("Root"))
            {
                Perm p = new Perm();
                p.setUserId(Shell.getUserList().UserList.get(i).getUserName());
                //System.out.println(Shell.getUserList().UserList.get(i).getUserName());
                p.setRw(3);
                p.setOwner(true);
                PermList.add(p);
            }
            else
            {
                Perm p = new Perm();
                p.setUserId(Shell.getUserList().UserList.get(i).getUserName());
                p.setRw(2);
                p.setOwner(false);
                PermList.add(p);
            }
        }
    }
    //Przyjmuje nazwa użytkownika i zwraca jakie ma uprawnienie
    public int userPerm(String userName)
    {
        int rw = -1;
                for(int i = 0; i< PermList.size(); i++)
                {
                    if(PermList.get(i).getUserID().equals(userName))
                    {
                        rw = PermList.get(i).getRW();
                    }
                }

        return rw;
    }

    //Przyjmuje imie uzytkownika do zmiany ktore na perm liscie albo istnieja, albo nie i trzeba dodac
    //wczesniej musi istniec nazwa na user_liscie
    public void changeParam(String UserID, int rw) throws Exception {

//        for (int i = 0; i < Shell.getUserList().size() ; i++)
//        {
//
//        }
        boolean existsUL = false;
        for(int u = 0; u<Shell.getUserList().UserList.size(); u++)
        {
            if(Shell.getUserList().UserList.get(u).getUserName().equals(UserID))
            {
                existsUL = true;
                break;
            }
        }
        if(!existsUL)
        {
            throw new Exception("Podanego uzytkownika nie ma na liscie uzytkownikow");
        }

        boolean flag = false;
        for(int j = 0; j < PermList.size(); j++)
        {
            if(PermList.get(j).getOwner())
            {
                if(Shell.getUserList().printLoggedUser().equals(PermList.get(j).getUserID())) {
                    flag = true;
                    break;
                }
            }
        }
        if(flag==true) {
            if (UserID.equals("Root")) {
                throw new Exception("Nie mozna zmienic uprawnien dla uzytkownika Root!");
            } else {
                boolean existsPL = false;
                for (int j = 0; j < PermList.size(); j++)
                    {
                        if (PermList.get(j).getUserID().equals(UserID))
                        {
                            PermList.get(j).setRw(rw);
                            System.out.println("Zmieniono uprawnienia uzytkownika "+UserID);
                            existsPL = true;
                            break;
                        }
                    }

                if(!existsPL)
                {
                    Perm p = new Perm();
                    p.setRw(rw);
                    p.setUserId(UserID);
                    //System.out.println("Dodano uzytkownika "+UserID+" do listy uprawnien");
                    p.setOwner(false);
                    PermList.add(p);
                }

            }
        }
        else
        {
            System.out.println("Użytkownik nie ma praw do zmiany uprawnień");
        }
    }

    public void printAllPermission()
    {
        for(Perm p:PermList)
        {
            System.out.println("ID: " + p.getUserID() + ", Prawa: " + p.getRW() + ", Właściciel(T/F): " + p.getOwner());
        }
    }

}
