package com.capstone.moa.service;

import com.capstone.moa.dto.FindNoticeByGroupIdResponse;
import com.capstone.moa.dto.WriteNoticeRequest;
import com.capstone.moa.entity.Group;
import com.capstone.moa.entity.GroupMember;
import com.capstone.moa.entity.Notice;
import com.capstone.moa.repository.GroupMemberRepository;
import com.capstone.moa.repository.GroupRepository;
import com.capstone.moa.repository.NoticeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final GroupRepository groupRepository;

    @Transactional
    public void createNotice(WriteNoticeRequest request, String email) {
        GroupMember leader = groupMemberRepository.findGroupLeader(request.getGroupId())
                .orElseThrow(() -> new IllegalArgumentException("Group Leader not found"));

        if (!leader.getMember().getEmail().equals(email)) {
            throw new IllegalArgumentException("You don't have authority");
        }

        noticeRepository.save(new Notice(leader.getGroup(), request.getTitle(), request.getContent()));
    }

    @Transactional
    public void deleteNotice(Long noticeId, Long groupLeaderId) {
        GroupMember leader = findGroupLeader(groupLeaderId);
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new IllegalArgumentException("Notice not found"));
        if (!Objects.equals(leader.getGroup(), notice.getGroup())) {
            throw new IllegalArgumentException("You don't have permission");
        }

        noticeRepository.delete(notice);
    }

    @Transactional(readOnly = true)
    public List<FindNoticeByGroupIdResponse> findNoticesByGroupId(Long groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Group not found"));

        return noticeRepository.findAllByGroup(group)
                .stream()
                .map(FindNoticeByGroupIdResponse::from)
                .toList();
    }

    private GroupMember findGroupLeader(Long groupLeaderId) {
        GroupMember leader = groupMemberRepository.findById(groupLeaderId)
                .orElseThrow(() -> new IllegalArgumentException("GroupMember not found"));
        if (!leader.isGroupLeader()) {
            throw new IllegalArgumentException("You are not the Leader of this group");
        }
        return leader;
    }
}
