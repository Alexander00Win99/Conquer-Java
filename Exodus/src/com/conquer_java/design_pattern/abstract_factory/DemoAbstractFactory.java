package com.conquer_java.design_pattern.abstract_factory;

/**
 * 【目标】：掌握抽象工厂模式
 *
 * 【结果】：完成
 *
 * 【抽象工厂模式】-Abstract Factory：只是提供接口，无须指定具体类。
 *
 * 数据库连接：MySQL, Oracle, SQL Server, DB2
 * 相同点：C/S模式
 *
 * JDK例子：Connection.java, Statement.java
 */
public class DemoAbstractFactory {

    public static void main(String[] args) {
        IDBComponent idbComponent = new MySQLConcreteComponent(); // ==> IDBComponent idbComponent = new OracleConcreteComponent();

        IConnection connection = idbComponent.getConnection();
        connection.connect();
        ICommand command = idbComponent.getCommand();
        command.command();
    }
}

interface IConnection {
    void connect();
}

interface ICommand {
    void command();
}

interface IDBComponent {
    IConnection getConnection();
    ICommand getCommand();
}

class MySQLConnection implements IConnection {
    @Override
    public void connect() {
        System.out.println("MySQL connect!");
    }
}

class MySQLCommand implements ICommand {
    @Override
    public void command() {
        System.out.println("MySQL command!");
    }
}

class MySQLConcreteComponent implements IDBComponent {
    @Override
    public IConnection getConnection() {
        return new MySQLConnection();
    }

    @Override
    public ICommand getCommand() {
        return new MySQLCommand();
    }
}

class OracleConnection implements IConnection {
    @Override
    public void connect() {
        System.out.println("Oracle connect!");
    }
}

class OracleCommand implements ICommand {
    @Override
    public void command() {
        System.out.println("Oracle command!");
    }
}

class OracleConcreteComponent implements IDBComponent {
    @Override
    public IConnection getConnection() {
        return new OracleConnection();
    }

    @Override
    public ICommand getCommand() {
        return new OracleCommand();
    }
}
