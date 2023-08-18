package com.zs.demo.downloadfile.lanzyun;

import com.zs.demo.downloadfile.download.DownloadInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CreateThreadDemo1 {
    public static List<DownloadInfo> mData = new ArrayList<>();
//    static List<DownloadInfo> syu= Collections.synchronizedList(mData);
    static LanDown lanDown= new LanDown();
    static class MyPost implements Runnable {
        private AtomicInteger finishedHorses;
        private final String fin_id;

        public MyPost(AtomicInteger finishedHorses, String fin_id) {
            this.finishedHorses = finishedHorses;
            this.fin_id = fin_id;
        }
        @Override
        public void run() {
            int position = finishedHorses.incrementAndGet();
            synchronized (finishedHorses) {
                String a=lanDown.LanOkhttp(JsonDis.lan_id.get(position));
                mData.add(new DownloadInfo(a));
                if (position == JsonDis.lan_id.size()) {
                    finishedHorses.notifyAll();
                }
            }
        }
    }
    public static void MyName() throws InterruptedException {
        AtomicInteger finishedHorses = new AtomicInteger(0);
        for (int i = 0; i < JsonDis.lan_id.size(); i++) {
            Thread horse = new Thread(new MyPost(finishedHorses,JsonDis.lan_id.get(i)));
            horse.start();
        }
        synchronized (finishedHorses) {
            while (finishedHorses.get() < JsonDis.lan_id.size()) {
                finishedHorses.wait();
            }
        }
        System.out.println("OK");
    }
}

