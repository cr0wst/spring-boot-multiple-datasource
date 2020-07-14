package com.smcrow.demo.farm

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
        basePackages = ["com.smcrow.demo.farm"],
        entityManagerFactoryRef = "farmEntityManager",
        transactionManagerRef = "farmTransactionManager"
)
class FarmDataSourceConfiguration {
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource-farm")
    fun farmDataSource(): DataSource = DataSourceBuilder.create().build()

    @Bean
    fun farmEntityManager(): LocalContainerEntityManagerFactoryBean =
            (LocalContainerEntityManagerFactoryBean()).apply {
                dataSource = farmDataSource()
                setPackagesToScan("com.smcrow.demo.farm")
                jpaVendorAdapter = HibernateJpaVendorAdapter()
            }

    @Bean
    fun farmTransactionManager() = JpaTransactionManager(farmEntityManager().`object`!!)
}