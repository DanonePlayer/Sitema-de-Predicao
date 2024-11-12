package br.ufac.edgeneoapi.service;

import br.ufac.edgeneoapi.dto.LogsDto;
import br.ufac.edgeneoapi.exception.RecursoNaoEncontradoException;
import br.ufac.edgeneoapi.mapper.LogsMapper;
import br.ufac.edgeneoapi.model.Logs;
import br.ufac.edgeneoapi.repository.LogsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LogsService implements IService<LogsDto> {

    @Autowired
    private LogsRepository logsRepository;

    @Autowired
    private LogsMapper logsMapper;

    @Override
    public List<LogsDto> getAll() {
        return logsRepository.findAll()
                .stream()
                .map(logsMapper::toLogsDto)
                .collect(Collectors.toList());
    }

    @Override
    public LogsDto getById(Long id) {
        Logs logs = logsRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Logs não encontrados com ID: " + id));
        return logsMapper.toLogsDto(logs);
    }

    @Override
    public LogsDto save(LogsDto logsDto) {
        Logs logs = logsMapper.toLogs(logsDto);
        Logs savedLogs = logsRepository.save(logs);
        return logsMapper.toLogsDto(savedLogs);
    }

    @Override
    public void delete(Long id) {
        Logs logs = logsRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Logs não encontrados com ID: " + id));
        logsRepository.delete(logs);
    }
}
