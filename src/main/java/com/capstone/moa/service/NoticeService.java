package com.capstone.moa.service;

import com.capstone.moa.dto.WriteNoticeRequest;
import com.capstone.moa.entity.GroupMember;
import com.capstone.moa.entity.Notice;
import com.capstone.moa.repository.GroupMemberRepository;
import com.capstone.moa.repository.NoticeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@AllArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final GroupMemberRepository groupMemberRepository;

    @Transactional
    public void createNotice(WriteNoticeRequest request) {
        GroupMember leader = findGroupLeader(request.getGroupMemberId());

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

    private GroupMember findGroupLeader(Long groupLeaderId) {
        GroupMember leader = groupMemberRepository.findById(groupLeaderId)
                .orElseThrow(() -> new IllegalArgumentException("GroupMember not found"));
        if (!leader.isGroupLeader()) {
            throw new IllegalArgumentException("You are not the Leader of this group");
        }
        return leader;
    }
}
