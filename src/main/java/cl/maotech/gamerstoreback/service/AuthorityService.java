package cl.maotech.gamerstoreback.service;

import cl.maotech.gamerstoreback.constant.Messages;
import cl.maotech.gamerstoreback.dto.AuthorityRequestDto;
import cl.maotech.gamerstoreback.dto.AuthorityResponseDto;
import cl.maotech.gamerstoreback.exception.ResourceNotFoundException;
import cl.maotech.gamerstoreback.mapper.AuthorityMapper;
import cl.maotech.gamerstoreback.model.Authority;
import cl.maotech.gamerstoreback.model.User;
import cl.maotech.gamerstoreback.repository.AuthorityRepository;
import cl.maotech.gamerstoreback.repository.UserRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorityService {

    private final AuthorityRepository authorityRepository;
    private final UserRespository userRespository;

    public List<AuthorityResponseDto> getAllAuthorities() {
        return authorityRepository.findAll().stream()
                .map(AuthorityMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public List<AuthorityResponseDto> getAuthoritiesByUserId(Long userId) {
        return authorityRepository.findByUserId(userId).stream()
                .map(AuthorityMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public AuthorityResponseDto getAuthorityById(Long id) {
        Authority authority = authorityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Messages.Authority.NOT_FOUND + id));
        return AuthorityMapper.toResponseDto(authority);
    }

    @Transactional
    public AuthorityResponseDto createAuthority(AuthorityRequestDto requestDto) {
        User user = userRespository.findById(requestDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException(Messages.Authority.USER_NOT_FOUND + requestDto.getUserId()));

        Authority authority = new Authority();
        authority.setUser(user);
        authority.setAuthority(requestDto.getAuthority());

        Authority savedAuthority = authorityRepository.save(authority);
        return AuthorityMapper.toResponseDto(savedAuthority);
    }

    @Transactional
    public AuthorityResponseDto updateAuthority(Long id, AuthorityRequestDto requestDto) {
        Authority authority = authorityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Messages.Authority.NOT_FOUND + id));

        User user = userRespository.findById(requestDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException(Messages.Authority.USER_NOT_FOUND + requestDto.getUserId()));

        authority.setUser(user);
        authority.setAuthority(requestDto.getAuthority());

        Authority updatedAuthority = authorityRepository.save(authority);
        return AuthorityMapper.toResponseDto(updatedAuthority);
    }

    @Transactional
    public void deleteAuthority(Long id) {
        Authority authority = authorityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Messages.Authority.NOT_FOUND + id));
        authorityRepository.delete(authority);
    }
}

