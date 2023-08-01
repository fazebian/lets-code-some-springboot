package com.sahia.letscode;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

@Slf4j
public class LetsCodeApplication {

	public static void main(String[] args) throws SQLException {

		var cs = new DefaultCustomerService();
		var all = cs.All();
		all.forEach(c-> log.info(c.toString()));

	}

	static class DefaultCustomerService{
		private final DataSource dataSource;

		DefaultCustomerService(){
			var dataSource = new DriverManagerDataSource("jdbc:postgresql://localhost/postgres",
					"postgres","1234");
			dataSource.setDriverClassName(Driver.class.getName());
			this.dataSource = dataSource;
		}
		Collection<Customer>All() throws SQLException {
			var listOfCostumers = new ArrayList<Customer>();

			try{
			try(var connection = dataSource.getConnection()){
					try(var stmt = connection.createStatement()){
						try(var resultSet = stmt.executeQuery("select * from customers")){
							while(resultSet.next()){
								var name = resultSet.getString("name");
								var id = resultSet.getInt("id");
								listOfCostumers.add(new Customer(id, name));
							}
						}
					}
			   }
			}//
			catch (Exception th){
				log.error("Misy erreur be ao fa antsoy za fa tsy haiko io",th);
			}
			return listOfCostumers;
		}
	}

	record Customer(Integer id ,String name){}
}
