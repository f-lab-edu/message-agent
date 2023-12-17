package com.kth.mssage.info.config;

import com.kth.mssage.repository.local.Local;
import com.kth.mssage.repository.local.LocalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class CSVWriter implements ItemWriter<Local> {

    private final LocalRepository localRepository;

    @Override
    public void write(Chunk<? extends Local> locals) {
        List<Local> localList = new ArrayList<>();

        locals.forEach(localList::add);

        localRepository.saveAll(localList);
    }
}
