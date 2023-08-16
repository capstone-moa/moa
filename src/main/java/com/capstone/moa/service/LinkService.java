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
    public void modifyGroupIntroLink(Long groupId, String githubLink, String projectLink) {
        Group group = findGroup(groupId);
        Link link = linkRepository.findByGroup(group)
                .orElseThrow(() -> new IllegalArgumentException("Link not found"));
        link.modifyGroupIntroLink(githubLink, projectLink);
    }

    @Transactional
    public void addFigmaLink(Long groupId, String figmaLink) {
        Group group = findGroup(groupId);
        Link link = group.getLink();
        link.modifyFigma(figmaLink);
    }

    @Transactional
    public void addErdCloudLink(Long groupId, String erdCloudLink) {
        Group group = findGroup(groupId);
        Link link = group.getLink();
        link.modifyErdCloud(erdCloudLink);
    }

    private Group findGroup(Long groupId) {
        return groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Group not found"));
    }
}
