import java.util.Random;

public class pro2 {
    static int a=0;//全局变量计算A总分
    static int b=0;//全局变量计算B总分
    public static void main(String[] args) throws InterruptedException {
        //打印表头
        System.out.println("                                       Thread A\t\t Thread B ");
        System.out.println("sleeptime    Random character    Points obtained    sleeptime    Random character    Points obtained");
        for(int i=0;i<4;i++) {
            if(i!=3) {//没有达到三次输出
                Judge judge1=new Judge();
                Thread judge=new Thread(judge1,"judge");
                judge.start();
                judge.join();
            }
            if(i==3){//计算总分打印赢家及得分
                if(a>b) {
                    System.out.println("\n\tthe winner is Thread A and the total Score is "+a);
                }
                else if(a==b) {
                    System.out.println("\n\tno winner");
                }
                else {
                    System.out.println("\n\tthe winner is Thread B and the total Score is "+b);
                }
            }
        }
    }

    static class Judge implements Runnable{//judge线程

        class Work extends Thread{//work线程
            public Work(String name) {
                super(name);
            }
            public int sleeptime;
            public char abc;
            @Override
            public void run() {
                synchronized(this) {
                    Random ran = new Random();
                    try {
                        int a=ran.nextInt(1000);//生成随机睡眠时间
                        sleeptime=a;
                        Thread.sleep(a);//休眠线程
                        char b=(char)('a'+ran.nextInt(26));//变为字母介于1/26之间
                        abc=b;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            public int gets() {return sleeptime;}//返回睡眠时间
            public char geta() {return abc;}//返回字母
        }

        int aScore=0;
        int bScore=0;
        void compares(Work workA,Work workB) {//比较大小并给分
            if(workA.geta()>workB.geta()) {
                aScore+=2;
            }
            else if(workA.geta()==workB.geta()) {
                aScore+=1;
                bScore+=1;
            }
            else {
                bScore+=2;
            }
        }

        @Override
        public void run() {
            Work workA=new Work("A");//新建workA
            Work workB=new Work("B");//新建workB

            try {
                workA.start();
                workA.join();
                workB.start();
                workB.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            compares(workA,workB);
            //打印数据
            System.out.println("  "+workA.gets()+"ms\t\t   "+workA.geta()+"\t\t\t"+aScore+"\t      "
                    +workB.gets()+"ms\t\t"+workB.geta()+"\t\t     "+bScore);
            a+=aScore;//累加A得分
            b+=bScore;//累加B得分
        }
    }
}
