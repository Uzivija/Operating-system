package Interpreter;


import java.util.LinkedList;
import java.util.*;
//import Process_Manager.*;

public class Scheduler {
   /* public void setRunning(PCB Running) {
        Running = running;
    }
    */


    LinkedList<PCB> Heap = new LinkedList<PCB>();

    int CurrTime;// zmienna inkrementujaca sie  co wykonany rozka, marcin ciechan ja obsluzy
    int min_granularity = 5; // minimalny czas, na jaki proces moze otryzmać procesor
    int wake_up_granularity=4;
    int period = 60; // okres, ustalany z gory, czas w jakim powinien każdy proces otrzymać procesor
    //PCB running = new PCB("proces2","filename2" );

    // pm.fork("p6", 4,"f6"); // tej zmiennej bedziemy przypisywać PCB procesu dzialajacego
    //PCB running=p7;

    public PCB dummy =new PCB("init","init.txt");
    public PCB running = dummy;

    LinkedList<PCB> sort(LinkedList<PCB> Heap) // funkcja sortujaca kolejke po CurrTime, CurrTime= vruntime +Currtime
    {

        Collections.sort(Heap, Comparator.comparingLong(PCB::getVirtualTime));

        return Heap;
    }

    void PrioToWeight(PCB pcb) //funkcja zwracajca przeliczone priorytety na wagi
    {
        int nice=pcb.getPriority()-120;
        double weight = 1-0.2*nice;
        pcb.setWeight(weight);
    }


    long HeapWeight() // funkcja zwracajaca sume wag w kolejce
    {
        long sum = 0;
        for(PCB p:Heap){
            if(p.getID()!=0){
                sum+=p.getWeight();
            }
        }
        return sum;
    }

    PCB min() { // funkcja wywolujaca process o najmniejszym czasie wirtualnym

        return Heap.get(0);
    }

    public LinkedList<PCB> Insert(PCB pcb) { // dodaje proces do kolejki i ja sortuje

        PrioToWeight(pcb);
        pcb.setVirtualTime(0+pcb.getVirtualTime());
        Heap.add(pcb);
        return sort(Heap);
    }

    public LinkedList<PCB> InsertFirst(PCB pcb) { // dodaje proces do kolejki i ja sortuje

        PrioToWeight(pcb);
        pcb.setVirtualTime(999999);
        Heap.add(pcb);
        return sort(Heap);
    }

    public Scheduler() {
        dummy.setState(PCB.StateList.Running);
    }


    public LinkedList<PCB> Delete(PCB pcb) // pytanie czy tu caly blok kontrolny procesu dostane czy tylko jego id
    {
        Heap.remove(pcb);
        return sort(Heap);
        /// wysylanie procesu do dominika mozna by tu zmiane stanu zrobic
    }

    void TimeSlice(PCB pcb) { // wylicza time slice dla procesu, tylko nie wiem czy
        double timeslice = period * pcb.getWeight() / HeapWeight();
        int timeslice_int=(int)timeslice;
        if (timeslice_int < min_granularity){
            timeslice_int = min_granularity;
        }
        pcb.setTimeSlice(timeslice_int);

    }

    void VirtualTime(PCB pcb) // zakładam,ze przy zmianie stanu na aktywny w baseTime wpiszemy czas, podczas ktorego process dostał procesor
    {
        double weight = pcb.getWeight();
        // long currTime=CurrTime;
        // long baseTime=pcb.getBaseTime();
        long VirtualTime = pcb.getVirtualTime();
        //long NewVirtualTime=(currTime-baseTime)*weight+VirtualTime;
        double NewVirtualTime = 1  + VirtualTime;
        pcb.setVirtualTime((int) NewVirtualTime);
        sort(Heap);
        //return NewVirtualTime; // nie wiem czy ten return bedzie potrzebny czy nie przerzuce sie na void
    }

    public void showProTimes(PCB proc){
        System.out.println(proc.getName()+" timeslice "+proc.getTimeSlice()+ " virtual "+proc.getVirtualTime());
    }

    public void showHeap(){
        for(PCB h:Heap){
            System.out.println(h.getName());
        }
    }

    public void check() {    //CurrTime=System.nanoTime();

        running.setTimeSlice(running.getTimeSlice() - 1);
        PCB min = min();
        VirtualTime(running);

        if (running.getState() == PCB.StateList.Terminated && (Heap.size() == 0)) // jesli proces został wykonany oraz wielkosc kolejki rowna sie zero to wykonujemy dummy
        {
            Delete(running);
            VirtualMemory.nowyproces(dummy);
            running = dummy;
        }// sprawdz czy heap.size gdy jet puste daje nam 0

        else {
            if ((running.getState() == PCB.StateList.Terminated) || (running.getState()==PCB.StateList.Waiting)) //  jesli aktywny proces sie wykonał to go usunąć
            {
                Delete(running);
                running = min;
                TimeSlice(running);
                //running.setBaseTime(CurrTime);
                running.setState(PCB.StateList.Running);

            } else {
                // VirtualTime(running);

                sort(Heap);
                PCB min1 = min();

                if (running.getTimeSlice() <= 0) {
                    running.setState(PCB.StateList.Ready);
                    running = min1;
                    TimeSlice(running);
                    //running.setBaseTime(CurrTime);
                    running.setState(PCB.StateList.Running);

                } else if ((running.getVirtualTime() - min1.getVirtualTime())>wake_up_granularity) //jesli czas wirtualny dzialajacego jest mniejszy od minimalnego
                {
                    running.setState(PCB.StateList.Ready);
                    running = min1;
                    TimeSlice(running);
                    //running.setBaseTime(CurrTime);
                    running.setState(PCB.StateList.Running);

                }
            }
        }
    }
}