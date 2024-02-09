package com.kth.message.weather.config.batch;

import com.kth.message.entity.Geometry;
import com.kth.message.weather.repository.GeometryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class CSVWriter implements ItemWriter<Geometry> {

    private final GeometryRepository geometryRepository;

    @Override
    public void write(Chunk<? extends Geometry> locals) {
        List<Geometry> geometryList = new ArrayList<>();

        locals.forEach(geometryList::add);

        geometryRepository.saveAll(geometryList);
    }
}
