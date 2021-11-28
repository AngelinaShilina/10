public class PingPong
{
    public static void main(String[] args)
    {
        Object lock = new Object();  //  lock будет необходим для подачи доступа в метод потокам.
        Thread ping = new Thread(new PingPongThread(lock, "Ping"));
        Thread pong = new Thread(new PingPongThread(lock, "Pong"));
        ping.start();  // Запуск потока "ping".
        pong.start();  // Запуск потока "pong".
    }
}


class PingPongThread implements Runnable
{
    private Object lock;  // Объявление поля lock.
    private String name;  // Объявление поля name.

    public PingPongThread(Object lock, String name)  // Конструктор
    {
        this.lock = lock;
        this.name = name;
    }

    @Override
    public void run()  // Метод для потока, который запустится после запуска потока методом start().
    {
        synchronized (lock)  // Данный блок может выполняться только одним потоком одновременно.
        {
            while(true)
            {
                System.out.println(name);  // Вывод текущего имени
                try  // Если нет исключений:
                {
                    Thread.sleep(1000);  //Пауза потока на 1 секунду
                }
                catch (InterruptedException e)  // Если есть исключение:
                {
                    e.printStackTrace();  // Указание той строки, в которой метод вызвал данное исключение (назавние метода)
                }
                lock.notify();  // Продолжение работы  потока, у которого был вызван метод wait().
                try  // Если нет исключений:
                {
                    lock.wait(1000);  // Пауза потока на 1 секунду или установка в режим ожидания, пока другим потоком не будет вызван метод notify().
                }
                catch (InterruptedException e)  // Если есть исключения:
                {
                    e.printStackTrace();  // Указание той строки, в которой метод вызвал данное исключение (название метода)
                }
            }
        }
    }
}