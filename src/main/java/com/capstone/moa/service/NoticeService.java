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
    public Long deleteNotice(Long noticeId, String email) {
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new IllegalArgumentException("Notice not found"));
        GroupMember leader = groupMemberRepository.findGroupLeader(notice.getGroup().getId())
                .orElseThrow(() -> new IllegalArgumentException("Group Leader not found"));

        if (!leader.getMember().getEmail().equals(email)) {
            throw new IllegalArgumentException("You don't have permission");
        }

        noticeRepository.delete(notice);
        return notice.getGroup().getId();
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
}
