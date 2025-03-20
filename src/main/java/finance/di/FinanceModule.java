package finance.di;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import finance.importexport.CSVDataImporter;
import finance.importexport.DataImporterFactory;
import finance.importexport.JSONDataImporter;
import finance.importexport.YAMLDataImporter;
import finance.repository.CachedFinanceRepository;
import finance.repository.FinanceRepository;
import finance.repository.InMemoryFinanceRepository;
import finance.service.FinanceManager;

public class FinanceModule extends AbstractModule {
    @Override
    protected void configure() {
        // Привязка менеджера и репозитория через DI
        bind(FinanceManager.class).in(Singleton.class);
        // Используем прокси-репозиторий, оборачивающий finance.repository.InMemoryFinanceRepository
        bind(InMemoryFinanceRepository.class).in(Singleton.class);
        bind(FinanceRepository.class).to(CachedFinanceRepository.class).in(Singleton.class);
        // Привязка импортёров и фабрики импортёров
        bind(JSONDataImporter.class).in(Singleton.class);
        bind(YAMLDataImporter.class).in(Singleton.class);
        bind(CSVDataImporter.class).in(Singleton.class);
        bind(DataImporterFactory.class).in(Singleton.class);
    }
}
