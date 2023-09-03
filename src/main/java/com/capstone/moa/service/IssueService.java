package com.capstone.moa.service;

import com.capstone.moa.dto.FindIssueResponse;
import com.capstone.moa.dto.WriteIssueRequest;
import com.capstone.moa.entity.Group;
import com.capstone.moa.entity.GroupMember;
import com.capstone.moa.entity.Issue;
import com.capstone.moa.repository.GroupMemberRepository;
import com.capstone.moa.repository.GroupRepository;
import com.capstone.moa.repository.IssueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IssueService {

    private final IssueRepository issueRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final GroupRepository groupRepository;

    @Transactional
    public void writeIssue(WriteIssueRequest request, Long memberId) {
        GroupMember writer = groupMemberRepository.findByGroupIdAndMemberId(request.getGroupId(), memberId)
                .orElseThrow(() -> new IllegalArgumentException("GroupMember not found"));

        issueRepository.save(new Issue(request.getTitle(), request.getContent(), writer));
    }

    @Transactional(readOnly = true)
    public List<FindIssueResponse> findIssueList(Long groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Group not found"));
        return issueRepository.findAllByGroup(group)
                .stream()
                .map(FindIssueResponse::from)
                .toList();
    }
}
