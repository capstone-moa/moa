package com.capstone.moa.service;

import com.capstone.moa.dto.WriteNoticeRequest;
import com.capstone.moa.entity.GroupMember;
import com.capstone.moa.entity.Notice;
import com.capstone.moa.repository.GroupMemberRepository;
import com.capstone.moa.repository.NoticeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final GroupMemberRepository groupMemberRepository;

    @Transactional
    public void createNotice(WriteNoticeRequest request) {
        GroupMember leader = groupMemberRepository.findById(request.getGroupMemberId())
                .orElseThrow(() -> new IllegalArgumentException("GroupMember not found"));
        if (!leader.isGroupLeader()) {
            throw new IllegalArgumentException("You are not the Leader of this group");
        }

        noticeRepository.save(new Notice(leader.getGroup(), request.getTitle(), request.getContent()));
    }
}
