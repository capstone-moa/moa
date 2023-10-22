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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

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
    public Page<FindIssueResponse> findIssueList(Long groupId, Pageable pageable) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Group not found"));
        return issueRepository.findAllIssueByGroup(group, pageable)
                .map(FindIssueResponse::from);
    }

    @Transactional(readOnly = true)
    public FindIssueResponse findIssueById(Long issueId) {
        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new IllegalArgumentException("Issue not found"));
        return FindIssueResponse.from(issue);
    }

    @Transactional
    public void deleteIssue(Long issueId, Long memberId) {
        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new IllegalArgumentException("Issue not found"));
        if (!Objects.equals(issue.getGroupMember().getMember().getId(), memberId)) {
            throw new IllegalArgumentException("You don't have authentication");
        }

        issueRepository.delete(issue);
    }
}
