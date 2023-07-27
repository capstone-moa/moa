package com.capstone.moa.service;

import com.capstone.moa.entity.Group;
import com.capstone.moa.entity.Link;
import com.capstone.moa.repository.GroupRepository;
import com.capstone.moa.repository.LinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LinkService {

    private final LinkRepository linkRepository;
    private final GroupRepository groupRepository;

    @Transactional
    public void addGithubLink(Long groupId, String githubLink) {
        Group group = findGroup(groupId);
        if (linkRepository.existsLinkByGroup(group)) {
            Link link = group.getLink();
            link.modifyGithub(githubLink);
        } else {
            linkRepository.save(Link.builder()
                    .github(githubLink)
                    .group(group)
                    .build());
        }
    }

    @Transactional
    public void addFigmaLink(Long groupId, String figmaLink) {
        Group group = findGroup(groupId);

        if (linkRepository.existsLinkByGroup(group)) {
            Link link = group.getLink();
            link.modifyFigma(figmaLink);
        } else {
            linkRepository.save(Link.builder()
                    .figma(figmaLink)
                    .group(group)
                    .build());
        }
    }

    private Group findGroup(Long groupId) {
        return groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Group not found"));
    }
}
