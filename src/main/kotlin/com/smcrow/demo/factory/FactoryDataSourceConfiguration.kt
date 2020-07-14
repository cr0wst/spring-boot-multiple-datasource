package com.smcrow.demo.factory

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import javax.sql.DataSource

@Configuration
@EnableJpaRepositories(
        basePackages = ["com.smcrow.demo.factory"],
        entityManagerFactoryRef = "factoryEntityManager",
        transactionManagerRef = "factoryTransactionManager"
)
class FactoryDataSourceConfiguration {
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource-factory")
    fun factoryDataSource(): DataSource = DataSourceBuilder.create().build()

    @Bean
    fun factoryEntityManager(): LocalContainerEntityManagerFactoryBean =
            (LocalContainerEntityManagerFactoryBean()).apply {
                dataSource = factoryDataSource()
                setPackagesToScan("com.smcrow.demo.factory")
                jpaVendorAdapter = HibernateJpaVendorAdapter()
            }

    @Bean
    fun factoryTransactionManager() = JpaTransactionManager(factoryEntityManager().`object`!!)
}